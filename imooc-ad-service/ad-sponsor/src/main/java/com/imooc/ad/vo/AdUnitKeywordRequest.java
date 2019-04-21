package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordRequest {

    private List<UnitKeyWord> unitKeyWords;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitKeyWord{
        private Long unitId;
        private String keywords;
    }
}
