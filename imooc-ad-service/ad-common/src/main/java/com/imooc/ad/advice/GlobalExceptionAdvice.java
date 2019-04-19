package com.imooc.ad.advice;

import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @description对异常处理然后进行响应
 * @date 2019/4/18
 * 在异常处理的时候一旦发生异常
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
//在异常处理的时候一旦发生异常 会将请求传进来
//可以看到我们把value标识出来我们想要对exception进行handler
//handler的目标是AdException
 //当我们代码主动抛出了throws new AdException 就会被这个GlobalExceptionAdvice捕获到
 //然后通过handler进行处理
 //统一的异常处理可以做很多扩展 比如你有adexcepiton这样的异常
  //你可以定义adException1 adException2 可以  handlerAdException1 handlerAdException2
  //多个异常处理方法然后用@ExceptionHandler(value = AdException.class)标识成不同的value
//那么你就可以对不同的异常有不同的响应信息 这样利于信息分类
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest request, AdException ex){
        //我们怎么告诉spring 我们要处理AdException这个异常呢
        CommonResponse<String> response = new CommonResponse<>(-1,"business error");
        response.setData(ex.getMessage());
        return response;
    }
}
