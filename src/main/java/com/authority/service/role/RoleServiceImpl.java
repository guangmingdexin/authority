package com.authority.service.role;

import com.authority.common.utils.Msg;
import com.authority.dao.mapper.role.RoleMapper;
import com.authority.dao.redis.GenerateId;
import com.authority.dao.redis.SimpleId;
import com.authority.pojo.po.Role;
import com.authority.pojo.vo.MenuToRole;
import com.authority.pojo.vo.RoleToMenu;
import com.authority.pojo.vo.VoMRId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName RoleServiceImpl
 * @Description 业务处理类
 * @Author guangmingdexin
 * @Date 2020/12/3 21:30
 * @Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private GenerateId generateId;

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = DuplicateKeyException.class)
    public HashMap<String, Object> createRole(Role role) {
        // 1. 判断 role 是否为空
        if(role == null) {
            return Msg.setResult("400", null, "创建失败, 角色不能为空");
        }
        // 判断 role_name 不存在 才能添加 否则给出提示
        // sudo

        System.out.println(role.toString());
        // 2. 获取 id 判断 id 是否为空，如果 id 不为空则创建一个
        if(role.getRoleId() == null) {
            try {
                // 3. 通过 redis 获取 当前 角色 Id 生成规则，自动生成组件
                String roleId = generateId.generateId(Role.class);
                System.out.println("生成的 Id : " + roleId);
                role.setRoleId(roleId);
                // 除了添加角色，还需要添加 权限
                roleMapper.createRole(role);
            }catch (DuplicateKeyException key) {
                role.setRoleId(UUID.randomUUID().toString());
                roleMapper.createRole(role);
            }
        }
        return Msg.setResult("200", role, "添加角色成功！");

    }

    @Override
    public void updateRole(Role role) {

        if(role == null) {
            throw new NullPointerException("角色不能为空！");
        }

        System.out.println(role.toString());

        if(role.getRoleId() == null) {
            throw new NullPointerException("角色Id 不能为空！");
        }

        roleMapper.updateRole(role);
    }

    @Override
    public void delRole(List<String> roleIds) {
        if(roleIds.size() < 1) {
            return;
        }

        roleMapper.delRole(roleIds);
    }

    @Override
    public List<Role> getAllRoles() {

        return roleMapper.getAllRoles();
    }

    @Override
    public RoleToMenu getRole(String roleId) {
        if(roleId == null) {
            throw new NullPointerException("id 不能为空！");
        }
        return roleMapper.getRole(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatchRoleMenu(String roleId, List<String> mrIds) {
        if(mrIds.size() > 0) {
            roleMapper.insertBatchRoleMenu(roleId, mrIds);
        }
    }
}
