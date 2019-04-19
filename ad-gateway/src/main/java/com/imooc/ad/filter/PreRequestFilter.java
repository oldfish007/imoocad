package com.imooc.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author
 * @description自定义过滤器
 * @date 2019/4/18
 *
 */
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {
//定义filter类型 总共有4种 pre post Routing filter
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
//定义filter执行的顺序  数字越小表示顺序越高 越先被执行
//定义同种类型的过滤器有很多个 哪个过滤器先执行 哪个过滤器后执行 就是通过filterOrder去定义
    @Override
    public int filterOrder() {
        return 0;
    }
//表示是否需要执行这个过滤器 true表示执行 false表示不执行 之所以有这个是因为我们可以定义一个过滤器
//但是当某些条件发生的时候才会执行
    @Override
    public boolean shouldFilter() {
        return true;// 需要永远执行
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();///请求上下文
        ctx.set("startTime",System.currentTimeMillis());
        return null;
    }
}
