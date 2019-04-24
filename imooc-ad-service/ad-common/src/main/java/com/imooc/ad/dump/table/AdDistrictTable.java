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
public class AdDistrictTable {
    private Long unitId;
    private String province;
    private String city;
}
