package com.authority.dao.mapper.menu;

import com.authority.pojo.vo.MenuToRole;
import com.authority.pojo.po.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Param
 * @Author guangmingdexin
 * @See
 **/
@Mapper
public interface MenuMapper {

    /**
     * 获取所有菜单
     *
     * @return 菜单集合
     */
    List<Menu> getAllMenus();

    void createMenu(Menu menu);

    void delMenu(List<String> menuIds);

    void updateMenu(Menu menu);

    List<MenuToRole> getAllMenusToRole();

    Menu getMenuById(String menuId);
}
