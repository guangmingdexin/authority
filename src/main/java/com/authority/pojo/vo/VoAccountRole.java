package com.authority.pojo.vo;

import com.authority.pojo.po.Account;
import com.authority.pojo.po.Role;

import java.util.List;

/**
 * @ClassName VoAccountRole
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/11 18:34
 * @Version 1.0
 **/
public class VoAccountRole {

    private Account account;

    private List<Role> roles;

    public VoAccountRole() {
    }

    public VoAccountRole(Account account, List<Role> roles) {
        this.account = account;
        this.roles = roles;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;

    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
