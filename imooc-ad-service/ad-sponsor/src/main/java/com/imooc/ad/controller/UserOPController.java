package com.imooc.ad.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.service.IUserService;
import com.imooc.ad.vo.AdPlanRequest;
import com.imooc.ad.vo.AdPlanResponse;
import com.imooc.ad.vo.CreateUserRequest;
import com.imooc.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @description
 * @date 2019/4/22
 */
@Slf4j
@RestController
public class UserOPController {
    private final IUserService iUserService;
    @Autowired
    public UserOPController(IUserService iUserService) {
        this.iUserService = iUserService;
    }
    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody
                                                     CreateUserRequest request) throws AdException {
        log.info("ad-sponsor:createUser->{}", JSON.toJSONString(request));
        return iUserService.createUser(request);
    }



}
