package com.drozdova.library.service;

import com.drozdova.library.bean.Author;
import com.drozdova.library.bean.Genre;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthorsList() throws ServiceException;
    boolean addAuthor(String name) throws ServiceException;
    void deleteAuthor(int id) throws ServiceException;
    void editAuthor(int id, String name) throws ServiceException;
}
