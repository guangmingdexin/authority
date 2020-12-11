package com.authority.pojo.vo;

import com.authority.pojo.po.Menu;
import com.authority.pojo.po.Role;

import java.util.Date;
import java.util.List;

/**
 * @ClassName MenuToRole
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/3 23:16
 * @Version 1.0
 **/
public class MenuToRole extends Menu {

    private List<Role> roles;

    public MenuToRole() {

    }

    public MenuToRole(String menuId, String menuName, String menuPatten, String menuType, String menuDescribe, Date createTime, List<Role> roles) {
        super(menuId, menuName, menuPatten, menuType, menuDescribe, createTime);
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "MenuToRole{" +
                "roles=" + roles +
                '}';
    }
}
