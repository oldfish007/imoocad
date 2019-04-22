package com.imooc.ad.advice;

import com.imooc.ad.annotation.IgnoreResponseAdvice;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author
 * @description对响应的统一拦截
 * @date 2019/4/18
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 这个响应是否应该去拦截 是否支持拦截他会根据一些判断条件去判断
     * @param methodParameter 根据方法参数去判断 也可以根据类去判断
     * @param aClass
     * @return
     */
    @Override
    @SuppressWarnings("all") //去除警告
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
       //拿得到类的声明被这个注解标识IgnoreResponseAdvice.class 我们就不想
       //他被CommonResponse所影响
        if(methodParameter.getDeclaringClass().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )){
            return false;
        }
        //第二种情况 方法声明的情况
        if(methodParameter.getMethod().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )){
            return false;
        }
        return true;
    }
//在写入响应之前你可以做一些操作

    /**
     * @param o 返回对象
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        //完全正常的数据
        CommonResponse<Object> response = new CommonResponse<>(0,"");
        //如果没有没有data 就直接返回response对象 代表对象为null
        if(null == o){
            return response;
            //代表是一个CommonResponse 那就不需要多加一层处理了
        }else if(o instanceof CommonResponse){
            response= (CommonResponse<Object>)o;
        }else{
            //一个普通的返回对象 data就是o
            response.setData(o);
        }
        return response;
    }
}
