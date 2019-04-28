package com.imooc.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author
 * @description模板对象
 * @date 2019/4/28
 * "tableList": [
 *     {
 *       "tableName": "ad_plan",
 *       "level": 2,
 *       "insert": [
 *       {"column": "id"},
 *       {"column": "user_id"},
 *       {"column": "plan_staus"},
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {

    private String tableName;
    private Integer level;
    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column{
        private String column;
    }
}
