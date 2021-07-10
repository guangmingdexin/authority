package com.authority.pojo.po;

import java.util.Date;

/**
 * @ClassName Account
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/2 19:07
 * @Version 1.0
 **/
public class Account extends User {


    private String email;

    private Date createTime;

    private String tel;

    private String code;

    // 可选 有默认值
    private String avatar;


    public Account() {
    }

    public Account(String userId, String userName, String password, boolean userState) {
        super(userId, userName, password, userState);
    }

    public Account(String userId, String userName, String password, boolean userState, String email, Date createTime,
                   String tel, String avatar) {
        super(userId, userName, password, userState);
        this.email = email;
        this.createTime = createTime;
        this.tel = tel;
        this.avatar = avatar;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


