package com.imooc.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description
 * @date 2019/4/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeTable {
    private Long adId;
    private String name;
    private Integer type;
    private Integer meterialType;//子类型
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String adUrl;
}
