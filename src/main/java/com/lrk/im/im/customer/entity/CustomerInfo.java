package com.lrk.im.im.customer.entity;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author cola
 * @since 2021-03-16
 */
@Data
@Accessors(chain = true)
@Document(collection = "userInfo")
public class CustomerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id
    private String id;
    /**
     * 登录账号
     */
    @NotNull(message = "账号不能为空")
    private String account;
    /**
     * 登录密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     * 邮箱
     */
    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 0男 1女
     */
    @NotNull(message = "性别不能为空")
    private Integer sex;
    /**
     * 头像地址
     */
    private String face;
    /**
     * 个人介绍
     */
    private String desc;
    /**
     * 注册时间
     */
    private String createDate;
    /**
     * 0可用  1不可用
     */
    private Integer status;
    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    @Transient
    private String code;

}
