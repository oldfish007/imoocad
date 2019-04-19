package com.imooc.ad.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @author
 * @description常量类
 * @date 2019/4/19
 */
@Getter
public enum  CommonStatus {
    VALID(1,"有效状态"),
    INVALID(0,"无效状态");
    private Integer status;
    private String message;


    CommonStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
