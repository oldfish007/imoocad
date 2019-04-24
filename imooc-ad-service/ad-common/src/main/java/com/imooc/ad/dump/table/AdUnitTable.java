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
public class AdUnitTable {
    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;
}
