package com.imooc.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanRequest {
    private Long id;
    private Long userId;
    private String planName;
    private String startDate;
    private String endDate;
//创建验证
    public boolean createValidate(){
        return userId!=null
                && !StringUtils.isEmpty(planName)
                && !StringUtils.isEmpty(startDate)
                && !StringUtils.isEmpty(endDate);
    }
//更新验证
    public boolean updateValidate(){
        return id!=null && userId!=null;
    }
 //删除验证
    public boolean deleteValidate(){
        return id!=null && userId!=null;
    }
}
