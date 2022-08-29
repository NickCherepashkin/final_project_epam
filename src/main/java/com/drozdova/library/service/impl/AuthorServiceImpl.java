package com.drozdova.library.service.impl;

import com.drozdova.library.bean.Author;
import com.drozdova.library.dao.AuthorDAO;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.DAOProvider;
import com.drozdova.library.service.AuthorService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.validation.AuthorDataValidator;
import com.drozdova.library.service.validation.ValidationException;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private static final AuthorDAO authorDAO = DAOProvider.getInstance().getAuthorDAO();
    private static final AuthorDataValidator validator = AuthorDataValidator.getInstance();

    @Override
    public List<Author> getAuthorsList() throws ServiceException {

        try {
            return authorDAO.getAuthorsList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addAuthor(String name) throws ServiceException {
        boolean result;

        validator.checkName(name);
        try {
            result = authorDAO.addAuthor(name);
            if (!result) {
                throw new ValidationException("Author with this name already exists");
            }
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteAuthor(int id) throws ServiceException {
        boolean result;
        try {
            result = authorDAO.deleteAuthor(id);
            if (!result) {
                throw new ValidationException("Author with this name already exists");
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void editAuthor(int id, String name) throws ServiceException {
        try {
            validator.checkName(name);
            authorDAO.editAuthor(id, name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
