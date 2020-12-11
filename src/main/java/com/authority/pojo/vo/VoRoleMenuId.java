package com.authority.pojo.vo;

import com.authority.pojo.po.Role;

import java.util.List;

/**
 * @ClassName VoRoleMenuId
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/7 20:13
 * @Version 1.0
 **/
public class VoRoleMenuId {

    private Role role;

    private List<String> mrIds;

    public VoRoleMenuId() {
    }

    public VoRoleMenuId(Role role, List<String> menuId) {
        this.role = role;
        this.mrIds = menuId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public List<String> getMrIds() {
        return mrIds;
    }

    public void setMrIds(List<String> mrIds) {
        this.mrIds = mrIds;
    }

    @Override
    public String toString() {
        return "VoRoleMenuId{" +
                "role=" + role +
                ", merIds=" + mrIds +
                '}';
    }
}
