package com.authority.pojo.po;

/**
 * @ClassName User
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/3 18:50
 * @Version 1.0
 **/
public class Admin extends User{


    public Admin() {
    }

    public Admin(String userId, String userName, String password, boolean userState) {
        super(userId, userName, password, userState);
    }


}
