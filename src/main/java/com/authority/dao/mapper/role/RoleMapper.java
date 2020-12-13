package com.authority.dao.mapper.role;

import com.authority.pojo.po.Role;
import com.authority.pojo.vo.MenuToRole;
import com.authority.pojo.vo.RoleToMenu;
import com.authority.pojo.vo.VoMRId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName RoleMapper
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/3 21:26
 * @Version 1.0
 **/
@Mapper
public interface RoleMapper {

    /**
     *
     * 添加角色
     *
     * @param role
     */
    void createRole(Role role);

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

    /**
     * 更加 角色 Id 获取 角色 以及 所有的 权限
     *
     * @param roleId 角色 Id
     * @return
     */
    RoleToMenu getRole(String roleId);

    /**
     * 创建角色时调用
     * 创建角色的同时 将角色与权限关联起来
     *
     * @param roleId 角色 id
     * @param mrIds  menu_id的集合
     */
    void insertBatchRoleMenu(@Param("roleId") String roleId, @Param("mrIds") List<String> mrIds);



    /**
     * 删除角色所有 权限
     *
     * @param roleId
     */
    void delAllMenu(String roleId);
}
