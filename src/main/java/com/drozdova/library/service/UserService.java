package com.drozdova.library.service;

import com.drozdova.library.bean.*;
import com.drozdova.library.dao.DAOException;

import java.util.List;

public interface UserService {
    User authorization(String login, String password) throws ServiceException;
    boolean registration(UserInfo userInfo, String password, String confirmPass, int role) throws ServiceException;
    UserInfo getUserInfo(int id) throws ServiceException;
    void updateUserInfo(UserInfo userInfo, int id, String pass, String confirmPass) throws ServiceException;
    List<FullUserInfo> getAllUsers() throws ServiceException;
    int deleteUser(int id, int idForDel, int role) throws ServiceException;
    List<Role> getUsersRoles() throws ServiceException;
}
