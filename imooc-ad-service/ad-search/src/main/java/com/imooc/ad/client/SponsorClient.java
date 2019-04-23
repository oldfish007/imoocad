package com.imooc.ad.client;

import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author
 * @description 同时还要思考一个问题 如果这个ad-sponsor这个服务下线了
 * 调用的时候就会抛出错误 如果级联的发生错误可能会引起雪崩
 * 一旦这个ad-sponsor这个服务不可用 那么调用adPlans的时候 实际上会返回这个  sponsorClientHystrix getPlans
 * @date 2019/4/23
 */
@FeignClient(value = "eureka-client-ad-sponsor",fallback = SponsorClientHystrix.class )//这样就指定了服务降级
public interface SponsorClient {

    @RequestMapping(value="/ad-sponsor/get/adPlan",
                method= RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(
            @RequestBody AdPlanGetRequest request
    );

}
