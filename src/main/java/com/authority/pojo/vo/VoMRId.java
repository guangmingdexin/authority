package com.authority.pojo.vo;

/**
 * @ClassName VoMRId
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/6 10:51
 * @Version 1.0
 **/
public class VoMRId {

    private String roleId;

    private String menuId;

    public VoMRId(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
