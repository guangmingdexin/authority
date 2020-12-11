package com.authority.service.menu;

import com.authority.pojo.vo.MenuToRole;
import com.authority.pojo.po.Menu;

import java.util.List;

/**
 * @Param
 * @Author guangmingdexin
 * @See
 **/
public interface MenuService {

    List<Menu> getAllMenus();

    void createMenu(Menu menu);

    void delMenu(List<String> menuIds);

    void updateMenu(Menu menu);

    List<MenuToRole> getAllMenusToRole();

    Menu getMenuById(String menuId);
}
