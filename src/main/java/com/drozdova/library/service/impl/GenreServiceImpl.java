package com.drozdova.library.service.impl;

import com.drozdova.library.bean.Genre;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.DAOProvider;
import com.drozdova.library.dao.GenreDAO;
import com.drozdova.library.service.GenreService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.validation.GenreDataValidator;
import com.drozdova.library.service.validation.ValidationException;

import java.util.List;

public class GenreServiceImpl implements GenreService {
    private static final GenreDAO genreDAO = DAOProvider.getInstance().getGenreDAO();
    private static final GenreDataValidator validator = GenreDataValidator.getInstance();

    @Override
    public List<Genre> getGenresList() throws ServiceException {

        try {
            return genreDAO.getGenresList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addGenre(String genre) throws ServiceException {
        boolean result;

        validator.checkGenre(genre);
        try {
            result = genreDAO.addGenre(genre);
            if (!result) {
                throw new ValidationException("Жанр '" + genre + "' уже добавлен");
            }
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteGenre(int id) throws ServiceException {
        try {
            genreDAO.deleteGenre(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void editGenre(int id, String title) throws ServiceException {
        try {
            validator.checkGenre(title);
            genreDAO.editGenre(id,title);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
