package com.drozdova.library.dao;

import com.drozdova.library.bean.Author;
import java.util.List;

public interface AuthorDAO {
    List<Author> getAuthorsList() throws DAOException;
    boolean addAuthor(String name) throws DAOException;
    boolean deleteAuthor(int id) throws DAOException;
    boolean editAuthor(int id, String name) throws DAOException;
}
