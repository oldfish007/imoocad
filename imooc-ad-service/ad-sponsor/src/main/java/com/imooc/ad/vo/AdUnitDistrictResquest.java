package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictResquest {

    private List<UnitDistirct> unitDistirctList;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitDistirct{
        private Long unitId;
        private String province;
        private String city;
    }
}
