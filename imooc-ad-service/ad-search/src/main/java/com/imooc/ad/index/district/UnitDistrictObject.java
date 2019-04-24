package com.imooc.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description广告单元
 * @date 2019/4/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDistrictObject {

    private Long   unitId;
    private String province;
    private String city;
    // <String      ,   Set<Long>>
    // province-city    unitId
}
