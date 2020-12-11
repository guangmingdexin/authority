package com.authority.pojo.po;

import java.util.Date;

/**
 * @ClassName Menu
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/3 22:49
 * @Version 1.0
 **/
public class Menu {

    private String menuId;

    private String menuName;

    private String menuPatten;

    private String menuType;

    private String menuDescribe;

    private Date createTime;

    public Menu() {
    }


    public Menu(String menuId, String menuName, String menuPatten, String menuType, String menuDescribe, Date createTime) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPatten = menuPatten;
        this.menuType = menuType;
        this.menuDescribe = menuDescribe;
        this.createTime = createTime;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuPatten() {
        return menuPatten;
    }

    public String getMenuType() {
        return menuType;
    }

    public String getMenuDescribe() {
        return menuDescribe;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuPatten(String menuPatten) {
        this.menuPatten = menuPatten;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public void setMenuDescribe(String menuDescribe) {
        this.menuDescribe = menuDescribe;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
