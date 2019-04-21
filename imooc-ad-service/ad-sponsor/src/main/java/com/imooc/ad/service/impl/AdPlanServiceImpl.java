package com.imooc.ad.service.impl;

import com.imooc.ad.constant.CommonStatus;
import com.imooc.ad.constant.Constants;
import com.imooc.ad.dao.AdPlanRepository;
import com.imooc.ad.dao.AdUserRepository;
import com.imooc.ad.entity.AdPlan;
import com.imooc.ad.entity.AdUser;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IAdPlanService;
import com.imooc.ad.utils.CommonUtils;
import com.imooc.ad.vo.AdPlanGetRequest;
import com.imooc.ad.vo.AdPlanRequest;
import com.imooc.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdPlanServiceImpl implements IAdPlanService {


    private AdPlanRepository adPlanRepository;
    private AdUserRepository adUserRepository;

    @Autowired
    public AdPlanServiceImpl(AdPlanRepository adPlanRepository, AdUserRepository adUserRepository) {
        this.adPlanRepository = adPlanRepository;
        this.adUserRepository = adUserRepository;
    }

    @Override
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws Exception {
        if(!request.createValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        // 确保关联的 User 是存在的
        Optional<AdUser> oldUser = adUserRepository.findById(request.getUserId());
        if(!oldUser.isPresent()){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdPlan oldPlan = adPlanRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if(oldPlan!=null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newAdplan = adPlanRepository.save(new AdPlan(
                request.getUserId()
                , request.getPlanName()
                , CommonUtils.parseStringDate(request.getStartDate())
                , CommonUtils.parseStringDate(request.getEndDate())));

        return new AdPlanResponse(newAdplan.getId(),newAdplan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws Exception {
        if(!request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return adPlanRepository.findAllByIdInAndUserId(request.getIds(),request.getUserId());
    }

    @Override
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws Exception {
        if(!request.updateValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        //先查出来
        AdPlan oldPlan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if(oldPlan==null){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        if(request.getPlanName()!=null){
            oldPlan.setPlanName(request.getPlanName());
        }
        if(request.getStartDate()!=null){
            oldPlan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
        }
        if(request.getEndDate()!=null){
            oldPlan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
        }
        oldPlan.setUpdateTime(new Date());
        oldPlan = adPlanRepository.save(oldPlan);

        return new AdPlanResponse(oldPlan.getId(),oldPlan.getPlanName());
    }

    @Override
    public void deleteAdPlan(AdPlanRequest request) throws Exception {

        if(!request.deleteValidate()){
            new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan oldPlan = adPlanRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if(oldPlan==null){
            new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        oldPlan.setPlanStatus(CommonStatus.INVALID.getStatus());
        oldPlan.setUpdateTime(new Date());
        adPlanRepository.save(oldPlan);
    }
}
