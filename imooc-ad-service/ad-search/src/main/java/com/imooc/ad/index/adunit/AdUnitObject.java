package com.imooc.ad.index.adunit;

import com.imooc.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description推广单元索引对象
 * @date 2019/4/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {

    private Long unitId;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;
    private AdPlanObject adPlanObject;
    public void update(AdUnitObject newObject){
        if(null!=newObject.getUnitId()){
            this.unitId = newObject.getUnitId();
        }
        if(null!=newObject.getAdPlanObject()){
            this.adPlanObject = newObject.getAdPlanObject();
        }
        if(null!=newObject.getPlanId()){
            this.planId = newObject.getPlanId();
        }
        if(null!=newObject.getPositionType()){
            this.positionType = newObject.getPositionType();
        }
        if(null!=newObject.getUnitStatus()){
            this.unitStatus = newObject.getUnitStatus();
        }
    }
}
