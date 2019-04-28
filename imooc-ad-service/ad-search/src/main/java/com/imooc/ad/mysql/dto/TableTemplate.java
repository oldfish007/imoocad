package com.imooc.ad.mysql.dto;

import com.imooc.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @description
 * @date 2019/4/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {
    //表名
    private String tableName;
    //等级
    private String level;
    /**
     *  OpType:"insert": [
     *         {"column": "id"},
     *         {"column": "user_id"},
     *         {"column": "plan_status"},
     *         {"column": "start_date"},
     *         {"column": "end_date"}
     *       ],
     */
    private Map<OpType, List<String>>  opTypeFieldSetMap = new HashMap<>();
    /**
     * 字段索引 -> 字段名  includedColumns={0, 1, 2}
     */
    private Map<Integer,String> posMap = new HashMap<>();

}
