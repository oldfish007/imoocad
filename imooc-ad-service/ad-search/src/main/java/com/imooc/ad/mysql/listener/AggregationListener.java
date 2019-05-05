package com.imooc.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.imooc.ad.mysql.TemplateHolder;
import com.imooc.ad.mysql.dto.BinlogRowData;
import com.imooc.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 总结：
 * 1.主要的目的就是对binlog日志的监听
 * 2.监听之后获取到对表有兴趣的监听器
 * 3.对binlog的处理器
 * 4.event.getData 转换为event.getData  binlogRwoData
 *  将rowdata传递给感兴趣的处理器 进行处理
 *
 *
 *
 */

@Slf4j
@Component
public class  AggregationListener  implements BinaryLogClient.EventListener {

    private String dbname;//数据库名称
    private String tableName;//数据表名
//每个库里面的每张表 都可以定义不同的监听方法  Ilistener对应监听的处理方法
    private Map<String,Ilistener> listenerMap = new HashMap<>();
//模版信息给她载入进来
    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genkey(String dbName,String tableName){

        return dbName+":"+tableName;
    }
//定义一个注册方法让外界可以去调用  注册监听器的方法
    public void register(String _dbName,String _tableName,
                         Ilistener ilistener){
        log.error("register:{}-{}",_dbName,_tableName);
        this.listenerMap.put(genkey(_dbName,_tableName),ilistener);
    }

//我们的目标是要把event解析成binlogRowData
// 把这个binlogrowdata传递给listener实现增量数据的更新
//event里面包含了 head data
    @Override
    public void onEvent(Event event) {

        EventType type  = event.getHeader().getEventType();
        log.debug("event type:{}",type);
        if(type==EventType.TABLE_MAP){
            TableMapEventData  data = event.getData();//data里面包含了所有的操作数据
            this.tableName = data.getTable();
            this.dbname = data.getDatabase();
            return;
        }
        //EventType跟mysql的版本是一样的
        if(type!=EventType.EXT_WRITE_ROWS
            && type!=EventType.EXT_UPDATE_ROWS
            && type!=EventType.EXT_WRITE_ROWS){
            return ;
        }
        //只有当写入和更新的时候 才会更新binlog日志
        //找出对表有兴趣的监听器
        String key = genkey(this.dbname,this.tableName);
        Ilistener listener = this.listenerMap.get(key);
        //如果等于null表示我们不关心这个表
        if(listener==null){
            log.debug("skip {}",key);
            return;
        }
        log.info("trigger event: {}",type.name());

        try{
            BinlogRowData rowData = binlogRowData(event.getData());
            if(rowData==null){
                return;
            }
            rowData.setType(type);
            listener.onEvent(rowData);
        }catch(Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }finally {
            this.dbname="";//清空刚才记录下来的数据库名
            this.tableName="";//清空刚才记录下来的表名
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData){
        if(eventData instanceof WriteRowsEventData){
          return ((WriteRowsEventData)eventData).getRows();
        }
        if(eventData instanceof  UpdateRowsEventData){
            return ((UpdateRowsEventData)eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }
        if(eventData instanceof DeleteRowsEventData){
            return ((DeleteRowsEventData)eventData).getRows();
        }
        //如果没有就返回一个空的
        return Collections.emptyList();
    }
    /** 们最终的目的是要将event 里面的data转变为binlogRowData
     * 实现event data转变为binlogRowData
     */
    private BinlogRowData binlogRowData(EventData eventData){
        TableTemplate table = templateHolder.getTable(tableName);
        if(null == table){
            log.warn("table {} not found",tableName);
            return null;
        }
//在每一次遍历的过程总实现填充 我们只关心变化后的数据
        List<Map<String,String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String,String> afterMap = new HashMap<>();
            int colLen = after.length;
            for (int ix=0;ix<colLen;ix++){
                // 取出当前位置对应的列名
                String colName = table.getPosMap().get(ix);
                // 如果没有则说明不关心这个列
                if(null == colName){
                    log.debug("ignore position:{}",ix);
                    continue;
                }
                String colValue = after[ix].toString();
                afterMap.put(colName,colValue);
            }
//更新的每一行的数据都实现了填充
            afterMapList.add(afterMap);
        }
        //创建binlogRowData对象
        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);

        return rowData;
    }


}
