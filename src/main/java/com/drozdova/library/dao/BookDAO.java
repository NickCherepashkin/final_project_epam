package com.drozdova.library.dao;

import com.drozdova.library.bean.Book;
import java.util.List;

public interface BookDAO {
    List<Book> getBookList() throws DAOException;
    List<Book> getNoveltyList() throws DAOException;
    int editBookInfo(Book book, int idAuthor) throws DAOException;
    boolean deleteBook(int idBook) throws DAOException;
    List<Book> getSortedBooks(String param) throws DAOException;
    List<Book> findBooks(String param) throws DAOException;
}
