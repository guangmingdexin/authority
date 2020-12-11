package com.authority.pojo.vo;

import com.authority.pojo.po.Account;

import java.util.List;

/**
 * @ClassName VoAccountRoleId
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/7 21:19
 * @Version 1.0
 **/
public class VoAccountRoleId {

    private Account account;

    private List<String> rIds;

    public VoAccountRoleId() {
    }

    public VoAccountRoleId(Account account, List<String> rIds) {
        this.account = account;
        this.rIds = rIds;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<String> getrIds() {
        return rIds;
    }

}
