package com.imooc.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description创意索引对象
 * @date 2019/4/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeObject {
    private Long adId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private Integer adUrl;//物料地址

    public void update(CreativeObject newObject){
        if(null!=newObject.getAdId()){
            this.adId = newObject.getAdId();
        }
        if(null!=newObject.getAdUrl()){
            this.adUrl= newObject.getAdUrl();
        }
        if(null!=newObject.getAuditStatus()){
            this.auditStatus = newObject.getAuditStatus();
        }
        if(null!=newObject.getHeight()){
            this.height = newObject.getHeight();
        }
        if(null!=newObject.getWidth()){
            this.width = newObject.getWidth();
        }
        if(null!=newObject.getType()){
            this.type = newObject.getType();
        }
        if(null!=newObject.getMaterialType()){
            this.materialType = newObject.getMaterialType();
        }

    }
}
