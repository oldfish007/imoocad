package com.imooc.ad.index.creativeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description创意单元多对多关联对象
 * @date 2019/4/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitObject {
    private Long unitId;//广告推广单元id
    private Long adId;//广告创意ID
}
