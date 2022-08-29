package com.drozdova.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class FullUserInfo implements Serializable {
    private static final long serialVersionUID = -3328219494858084337L;
    private User user;
    private UserInfo userInfo;

    public FullUserInfo(User user, UserInfo userInfo) {
        this.user = user;
        this.userInfo = userInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullUserInfo that = (FullUserInfo) o;
        return Objects.equals(getUser(), that.getUser()) && Objects.equals(getUserInfo(), that.getUserInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getUserInfo());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "user=" + user +
                ", userInfo=" + userInfo +
                '}';
    }
}
