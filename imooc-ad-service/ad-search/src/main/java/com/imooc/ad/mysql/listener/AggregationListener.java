package com.imooc.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.imooc.ad.mysql.TemplateHolder;
import com.imooc.ad.mysql.dto.BinlogRowData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
        log.error("event type:{}",type);
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

    /**我们最终的目的是要将event 里面的data转变为binlogRowData
     *实现event data转变为binlogRowData
     */
    private BinlogRowData binlogRowData(EventData eventData){

        return null;
    }


}
