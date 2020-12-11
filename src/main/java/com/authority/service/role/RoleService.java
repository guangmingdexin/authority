package com.authority.service.role;

import com.authority.pojo.po.Role;
import com.authority.pojo.vo.MenuToRole;
import com.authority.pojo.vo.RoleToMenu;
import com.authority.pojo.vo.VoMRId;

import java.util.HashMap;
import java.util.List;

/**
 * @Param
 * @Author guangmingdexin
 * @See
 **/
public interface RoleService {

    /**
     *
     * 添加角色
     *
     * @param role
     * @return Msg
     */
    HashMap<String, Object> createRole(Role role);

    /**
     * 更新角色
     *
     * @param role 角色对象
     */
    void updateRole(Role role);

    /**
     * 删除角色
     *
     * @param roleIds 角色 id 集合
     */
    void delRole(List<String> roleIds);

    /**
     * 获取所有角色
     *
     * @return Role 集合
     */
    List<Role> getAllRoles();

    RoleToMenu getRole(String roleId);

    void insertBatchRoleMenu(String roleId, List<String> mrIds);
}
