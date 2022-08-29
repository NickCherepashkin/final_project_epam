package com.drozdova.library.service;

import com.drozdova.library.bean.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getGenresList() throws ServiceException;
    boolean addGenre(String genre) throws ServiceException;
    void deleteGenre(int id) throws ServiceException;
    void editGenre(int id, String title) throws ServiceException;
}
