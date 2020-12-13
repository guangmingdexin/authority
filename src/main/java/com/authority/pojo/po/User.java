package com.authority.pojo.po;

/**
 * @ClassName User
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/5 9:29
 * @Version 1.0
 **/
public abstract class User {

    protected String userId;

    protected String userName;

    protected String password;

    protected boolean userState;

    public User() {
    }

    public User(String userId, String userName, String password, boolean userState) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userState = userState;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getUserState() {
        return userState;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userState=" + userState +
                '}';
    }
}
