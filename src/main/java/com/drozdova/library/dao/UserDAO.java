package com.drozdova.library.dao;

import com.drozdova.library.bean.FullUserInfo;
import com.drozdova.library.bean.Role;
import com.drozdova.library.bean.User;
import com.drozdova.library.bean.UserInfo;

import java.util.List;

public interface UserDAO {
    User authorization(String login, String password) throws DAOException;
    boolean registration(UserInfo userInfo, String password, int role) throws DAOException;
    UserInfo getUserInfo(int id) throws DAOException;
    void updateUserInfo(UserInfo userInfo, int id, String pass) throws DAOException;
    List<FullUserInfo> getAllUsers() throws DAOException;
    int deleteUser(int id, int role) throws DAOException;
    List<Role> getUsersRoles() throws DAOException;
}
