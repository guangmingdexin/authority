package com.authority.pojo.vo;

import com.authority.pojo.po.Menu;
import com.authority.pojo.po.Role;

import java.util.Date;
import java.util.List;

/**
 * @ClassName RoleToMenu
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/4 17:09
 * @Version 1.0
 **/
public class RoleToMenu extends Role {

    private List<Menu> menus;

    public RoleToMenu() {
    }

    public RoleToMenu(String roleId, String roleName, Date createTime, String roleState, String roleDescribe, List<Menu> menus) {
        super(roleId, roleName, createTime, roleState, roleDescribe);
        this.menus = menus;
    }

    public List<Menu> getMenus() {
        return menus;
    }
}
