package com.imooc.ad.client;

import com.imooc.ad.client.vo.AdPlan;
import com.imooc.ad.client.vo.AdPlanGetRequest;
import com.imooc.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @description熔断降级
 * @date 2019/4/23
 */
@Component
public class SponsorClientHystrix implements SponsorClient {
    /**
     * 一旦发生调用sponsor的过程中发生错误了立马就会做服务降级
     * 就会在这个方法里面降级友好提示
     * @param request
     * @return
     */
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1,"eureka-client-ad-sponsor-error");
    }
}
