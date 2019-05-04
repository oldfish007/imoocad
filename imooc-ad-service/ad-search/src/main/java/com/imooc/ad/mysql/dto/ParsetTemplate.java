package com.imooc.ad.mysql.dto;

import com.imooc.ad.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * @author
 * @description
 * @date 2019/4/30
 */
@Data
public class ParsetTemplate {

    private String database;
    //tableName   tableTemplate
    private Map<String,TableTemplate> tableTemplateMap = new HashMap<>();
  //解析模板文件
    public static ParsetTemplate parse(Template _template){
        ParsetTemplate parseTemplate = new ParsetTemplate();
        //
        parseTemplate.setDatabase(_template.getDatabase());
        //遍历Template 里面的list<JsonTable>
        for (JsonTable jsonTable : _template.getTableList()) {
            String tableName = jsonTable.getTableName();
            Integer level = jsonTable.getLevel();
//填充TableTemplate
            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(tableName);
            tableTemplate.setLevel(level.toString());
            parseTemplate.tableTemplateMap.put(tableName,tableTemplate);
//获取一个放所有操作类型
            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();
//给tableTemplate opTypeFieldSetMap 添加各种操作类型
            for (JsonTable.Column column : jsonTable.getInsert()) {
                getAndCreateIfNeed(
                        OpType.ADD,
                        opTypeFieldSetMap,
                        ArrayList::new
                         ).add(column.getColumn());
            }
            for (JsonTable.Column column : jsonTable.getUpdate()) {
                getAndCreateIfNeed(
                        OpType.UPDATE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
            for (JsonTable.Column column : jsonTable.getDelete()) {
                getAndCreateIfNeed(
                        OpType.DELETE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
        }
        return parseTemplate;
    }


    private static<T,R> R getAndCreateIfNeed(T key, Map<T, R> map,
                                             Supplier<R> factory){
        //若key对应的value为空，会将第二个参数的返回值存入并返回
        return map.computeIfAbsent(key,k -> factory.get());
    }
}
