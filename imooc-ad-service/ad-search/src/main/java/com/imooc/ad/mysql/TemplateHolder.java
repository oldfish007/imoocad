package com.imooc.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.mysql.constant.OpType;
import com.imooc.ad.mysql.dto.ParsetTemplate;
import com.imooc.ad.mysql.dto.TableTemplate;
import com.imooc.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TemplateHolder {

    private  ParsetTemplate parsetTemplate;
    private final JdbcTemplate jdbcTemplate;
//用于填写 sql 语句
    private String SQL_SCHEMA= "select table_schema,table_name,column_name,ordinal_position " +
        "from information_schema.columns " +
        "where table_schema=? and table_name=?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * spring加载的时候就会执行这个方法
     */
    @PostConstruct
    public void init(){
        loadJson("template.json");//加载json文件
    }

    public TableTemplate getTable(String tableName){
        return parsetTemplate.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String path){
        //类加载器
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inStream = cl.getResourceAsStream(path);
        //通过json文件实例化Template
        try {
            Template template = JSON.parseObject(
                    inStream,
                    Charset.defaultCharset(),
                    Template.class
            );
        //解析这个模版文件成为对象
            this.parsetTemplate = ParsetTemplate.parse(template);
            loadMeta();
        } catch (IOException e) {
            log.error(e.getMessage());

            throw new RuntimeException("fail to parse json file");
        }
    }

    public void loadMeta(){
//对每张表进行for循环
        for (Map.Entry<String, TableTemplate> entry:
                parsetTemplate.getTableTemplateMap().entrySet()){
//拿到Map<String, TableTemplate> tableTemplateMap entry.getValue
            TableTemplate table = entry.getValue();//取得就是TableTemplate

            List<String> updateFields = table.getOpTypeFieldSetMap().get(
                    OpType.ADD
            );
            List<String> insertFields = table.getOpTypeFieldSetMap().get(
                    OpType.UPDATE
            );
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(
                    OpType.DELETE
            );
//根据语句查询 ，语句，数据库名称 表名
            jdbcTemplate.query(SQL_SCHEMA,new Object[]{
                    parsetTemplate.getDatabase(),
                    table.getTableName()
            },(rs,i)->{
                //根据结果取到索引
                    int pos = rs.getInt("ORDINAL_POSITION");
                //根据列名COLUMN_NAME 取到这个表的列名
                    String colName = rs.getString("COLUMN_NAME");
                    if((null!=updateFields && updateFields.contains(colName))
                    ||(null !=insertFields && insertFields.contains(colName))
                    ||(null != deleteFields&& deleteFields.contains(colName))){
                        //这样就把TableTemplate 里面的Map<Integer,String> posMap 索引号 列名填充进去了
                        table.getPosMap().put(pos-1,colName);
                    }
                    return null;
            });
        }
    }
}
