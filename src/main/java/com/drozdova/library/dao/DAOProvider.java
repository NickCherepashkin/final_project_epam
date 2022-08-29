package com.drozdova.library.dao;

import com.drozdova.library.dao.impl.*;

public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();

    private final UserDAO userDAO = new SQLUserDAO();
    private final BookDAO bookDAO = new SQLBookDAO();
    private final GenreDAO genreDAO = new SQLGenreDAO();
    private final AuthorDAO authorDAO = new SQLAuthorDAO();
    private final OrderDAO orderDAO = new SQLOrderDAO();

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public BookDAO getBookDAO() {
        return bookDAO;
    }

    public GenreDAO getGenreDAO() {
        return genreDAO;
    }

    public AuthorDAO getAuthorDAO() {
        return authorDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }
}