package com.imooc.ad.exception;

/**
 * @author
 * @description统一异常处理
 * @date 2019/4/18
 * 你需要处理检索系统的异常或者是投放系统的异常
 * 可以单独定义这些异常类
 * 单独针对这些类做单独的异常处理
 * 去考虑哪些异常会影响到用户体验
 */
public class AdException extends  Exception{


    public AdException(String message) {
        super(message);
    }
}
