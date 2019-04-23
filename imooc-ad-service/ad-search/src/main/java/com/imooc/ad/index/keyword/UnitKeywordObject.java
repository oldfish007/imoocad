package com.imooc.ad.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 * @description推广单元的索引对象
 * @date 2019/4/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitKeywordObject {
    private Long unitId;
    private String keyword;
}
