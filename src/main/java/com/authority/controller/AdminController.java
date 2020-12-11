package com.authority.controller;

import com.authority.common.utils.Msg;
import com.authority.pojo.po.Account;
import com.authority.pojo.po.Menu;
import com.authority.pojo.po.Role;
import com.authority.pojo.vo.RoleToMenu;
import com.authority.pojo.vo.VoAccountRoleId;
import com.authority.pojo.vo.VoRoleMenuId;
import com.authority.service.menu.MenuService;
import com.authority.service.role.RoleService;
import com.authority.service.user.account.AccountUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/3 20:25
 * @Version 1.0
 **/

@RestController
@CrossOrigin
public class AdminController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountUserService userService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/admin/create_role")
    public HashMap<String, Object> createRole(HttpServletRequest request, @RequestBody VoRoleMenuId params) throws IOException {

        System.out.println(params.toString());
        Role role = params.getRole();
        List<String> mrIds = params.getMrIds();
        // 插入 role_menu
        roleService.insertBatchRoleMenu(role.getRoleId(), mrIds);
        return roleService.createRole(role);
    }

    @GetMapping("/admin/roles/all")
    public HashMap<String, Object> getAllRoles() {

        return Msg.setResult("200", roleService.getAllRoles(), "查询成功！");
    }

    @GetMapping("/admin/roles/id/{roleId}")
    public HashMap<String, Object> getRole(@PathVariable("roleId") String roleId) {

        RoleToMenu role = roleService.getRole(roleId);
        return Msg.setResult("200", role, "success");
    }

    @PutMapping("/admin/role/id")
    public HashMap<String, Object> updRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return Msg.setResult("200", role, "success");
    }

    @DeleteMapping("/admin/role/ids")
    public HashMap<String, Object> delRoles(@RequestBody List<String> roleIds) {
        roleService.delRole(roleIds);
        return Msg.setResult("200", roleIds, "success");
    }

    @PostMapping("/admin/create_account")
    public HashMap<String, Object> createAccount(@RequestBody VoAccountRoleId params) {
        userService.createAccount(params.getAccount());
        userService.insertBatchAR(params.getAccount().getUserId(), params.getrIds());
        return Msg.setResult("200", null, "success");
    }

    @GetMapping("/admin/account/all")
    public HashMap<String, Object> getAllAccount() {
        return Msg.setResult("200", userService.getAllAccount(), "success");
    }

    @GetMapping("/admin/account/role/id/{accountId}")
    public HashMap<String, Object> getAccountRoles(@PathVariable("accountId") String accountId) {
        return Msg.setResult("200", userService.getAccountRole(accountId), "success");
    }

    @PutMapping("/admin/account/id")
    public HashMap<String, Object> updAccount(@RequestBody Account account) {
        userService.updAccount(account);
        return Msg.setResult("200", account, "success");
    }

    @DeleteMapping("/admin/account/ids")
    public HashMap<String, Object> delAccount(@RequestBody List<String> accountIds) {
        userService.delAccount(accountIds);
        System.out.println(accountIds);
        return Msg.setResult("200", accountIds, "success");
    }

    @GetMapping("/admin/menu/all")
    public HashMap<String, Object> getAllMenus() {
        return Msg.setResult("200", menuService.getAllMenus(), "success");
    }

    @PutMapping("/admin/menu/id")
    public HashMap<String, Object> updMenuById(@RequestBody Menu menu) {
        menuService.updateMenu(menu);
        return Msg.setResult("200", menu, "success");
    }

    @PostMapping("/admin/create_menu")
    public HashMap<String, Object> createMenu(@RequestBody Menu menu) {
        menuService.createMenu(menu);
        return Msg.setResult("200", menu, "success");
    }

    @GetMapping("/admin/menu/id/{menuId}")
    public HashMap<String, Object> getMenu(@PathVariable("menuId") String menuId) {
        System.out.println("menuId: " + menuId);
        return Msg.setResult("200", menuService.getMenuById(menuId), "success");
    }

    @DeleteMapping("/admin/menu/ids")
    public HashMap<String, Object> delMenu(@RequestBody List<String> menuIds) {
        menuService.delMenu(menuIds);
        return Msg.setResult("200", menuIds, "success");
    }

}
