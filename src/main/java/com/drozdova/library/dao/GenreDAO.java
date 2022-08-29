package com.drozdova.library.dao;

import com.drozdova.library.bean.Genre;

import java.util.List;

public interface GenreDAO {
    List<Genre> getGenresList() throws DAOException;
    boolean addGenre(String genre) throws DAOException;
    void deleteGenre(int id) throws DAOException;
    boolean editGenre(int id, String title) throws DAOException;
}
