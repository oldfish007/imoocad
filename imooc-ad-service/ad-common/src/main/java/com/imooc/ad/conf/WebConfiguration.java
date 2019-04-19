package com.imooc.ad.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author
 * @description统一消息转换器
 * @date 2019/4/18
 * 以后我们在广告系统的其它模块引用了这个ad-common之后所有请求的响应和请求对象的处理
 * 都会通过消息转换器做一层过滤的处理 这个消息转换器只有一个 MappingJackson2HttpMessageConverter
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    //springboot里面给我们提供了多个http的消息转换器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(new MappingJackson2HttpMessageConverter());//可以实现将java对象转换成json对象借助fastjson
    }
}
