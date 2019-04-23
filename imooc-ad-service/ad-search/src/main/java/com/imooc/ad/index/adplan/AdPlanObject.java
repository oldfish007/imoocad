package com.imooc.ad.index.adplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author
 * @description推广计划建立索引
 * @date 2019/4/23\
 * AdPlanObject作为一个索引对象存在
 * 我们会把数据表的实体类翻译成一个实体对象 每一个实体类
 * 都会定义一个实体对象 然后会建立对应的索引
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanObject {
    private Long planId;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;

    public void update(AdPlanObject newObj){
        if(null!=newObj.getPlanId()){
            this.planId = newObj.getPlanId();
        }
        if(null!=newObj.getUserId()){
            this.userId = newObj.getUserId();
        }
        if(null!=newObj.getPlanStatus()){
            this.planStatus = newObj.getPlanStatus();
        }
        if(null!=newObj.getStartDate()){
            this.startDate= newObj.getStartDate();
        }
        if(null!=newObj.getEndDate()){
            this.endDate = newObj.getEndDate();
        }
    }
}
