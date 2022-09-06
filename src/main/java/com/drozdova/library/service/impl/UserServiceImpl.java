package com.drozdova.library.service.impl;

import com.drozdova.library.bean.FullUserInfo;
import com.drozdova.library.bean.Role;
import com.drozdova.library.bean.User;
import com.drozdova.library.bean.UserInfo;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.DAOProvider;
import com.drozdova.library.dao.UserDAO;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.UserService;
import com.drozdova.library.service.validation.UserDataValidator;
import com.drozdova.library.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
    private static final UserDataValidator validator = UserDataValidator.getInstance();
    private static final UserDAO userDAO = DAOProvider.getInstance().getUserDAO();

    @Override
    public User authorization(String login, String password) throws ServiceException {
        if (login == null || password == null) {
            LOG.info("login or password is null");
            return new User();
        }
        validator.checkEmail(login);
        try {
            return userDAO.authorization(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean registration(UserInfo userInfo, String password, String confirmPass, int role) throws ServiceException {
        boolean regResult;

        validator.validateUserInfo(userInfo);
        validator.checkPassword(password, confirmPass);
        if (password.isEmpty() && confirmPass.isEmpty()) {
            throw new ValidationException("Поля для ввода пароля не могут быть пустыми.");
        }

        try {
            regResult = userDAO.registration(userInfo, password, role);
            if (!regResult) {
                throw new ValidationException("Пользователь с таким именем уже существует.");
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return regResult;
    }

    @Override
    public UserInfo getUserInfo(int id) throws ServiceException {
        try {
            return userDAO.getUserInfo(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, int id, String pass, String confirmPass) throws ServiceException{
        try {
            validator.validateUserInfo(userInfo);
            if (pass !=null && confirmPass != null) {
                validator.checkPassword(pass, confirmPass);
            }
            userDAO.updateUserInfo(userInfo, id, pass);
        }  catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<FullUserInfo> getAllUsers() throws ServiceException{
        try {
            return userDAO.getAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int deleteUser(int id, int idForDel, int role) throws ServiceException {
        try {
            validator.isNotMyId(id, idForDel);
            return userDAO.deleteUser(idForDel, role);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Role> getUsersRoles() throws ServiceException {
        try {
            return userDAO.getUsersRoles();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
