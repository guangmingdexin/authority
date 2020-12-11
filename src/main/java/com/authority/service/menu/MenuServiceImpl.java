package com.authority.service.menu;

import com.authority.dao.mapper.menu.MenuMapper;
import com.authority.dao.redis.GenerateId;
import com.authority.pojo.vo.MenuToRole;
import com.authority.pojo.po.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/3 23:01
 * @Version 1.0
 **/
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private GenerateId generateId;

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    @Override
    @NonNull
    @Transactional(rollbackFor = Exception.class, noRollbackFor = DuplicateKeyException.class)
    public void createMenu(Menu menu) {
        if(menu.getMenuId() == null) {
            String menuId = generateId.generateId(Menu.class);
            menu.setMenuId(menuId);
            try {
                menuMapper.createMenu(menu);
            }catch (DuplicateKeyException key) {
                menu.setMenuId(UUID.randomUUID().toString());
                menuMapper.createMenu(menu);
            }
        }
    }

    @Override
    public void delMenu(List<String> menuIds) {
        menuMapper.delMenu(menuIds);
    }

    @Override
    @NonNull
    public void updateMenu(Menu menu) {
        if(menu.getMenuId() == null) {
            return;
        }
        menuMapper.updateMenu(menu);
    }

    @Override
    public List<MenuToRole> getAllMenusToRole() {
        return menuMapper.getAllMenusToRole();
    }

    @Override
    public Menu getMenuById(String menuId) {
        return menuMapper.getMenuById(menuId);
    }
}
