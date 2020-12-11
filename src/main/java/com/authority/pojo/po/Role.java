package com.authority.pojo.po;

import com.authority.common.utils.annotation.Id;

import java.util.Date;

/**
 * @ClassName Role
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/2 19:15
 * @Version 1.0
 **/
public class Role {

    @Id(isPrimary = true)
    private String roleId;

    private String roleName;

    private Date createTime;

    private String roleState;

    // 角色描述 可选
    private String roleDescribe;

    public Role() {
    }

    public Role(String roleId, String roleName, Date createTime, String roleState, String roleDescribe) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.createTime = createTime;
        this.roleState = roleState;
        this.roleDescribe = roleDescribe;
    }


    @Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", createTime=" + createTime +
                ", roleState='" + roleState + '\'' +
                ", roleDescribe='" + roleDescribe + '\'' +
                '}';
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRoleState() {
        return roleState;
    }

    public void setRoleState(String roleState) {
        this.roleState = roleState;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }
}
