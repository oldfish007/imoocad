package com.imooc.ad.entity;

import com.imooc.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author
 * @description用户实体对象
 * @date 2019/4/19
 * 注意企业级开发中的表字段都是不为null
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ad_user")
public class AdUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    @Basic
    @Column(name="username",nullable = false)
    private String username;
    @Basic
    @Column(name="token",nullable = false)
    private String token;//这个用于接入其它系统 用户信息不公开展示给一个token比较合适
    @Basic
    @Column(name="user_status",nullable = false)
    private Integer userstatus;
    @Basic
    @Column(name="create_time",nullable = false)
    private Date createTime;
    @Basic
    @Column(name="update_time",nullable = false)
    private Date updateTime;

    public AdUser(String username, String token) {
        this.username = username;
        this.token = token;
        this.userstatus = CommonStatus.VALID.getStatus();
        this.createTime= new Date();
        this.updateTime = this.createTime;
    }
}
