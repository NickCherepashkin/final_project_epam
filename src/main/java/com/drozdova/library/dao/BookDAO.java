package com.drozdova.library.dao;

import com.drozdova.library.bean.Book;
import java.util.List;

public interface BookDAO {
    List<Book> getBookList(int offset, int noOfRecords) throws DAOException;
    int getNoOfRecords();
    List<Book> getNoveltyList() throws DAOException;
    int editBookInfo(Book book, int idAuthor) throws DAOException;
    boolean deleteBook(int idBook) throws DAOException;
    List<Book> getSortedBooks(String param, int offset, int noOfRecords) throws DAOException;
    List<Book> findBooks(String param, int offset, int noOfRecords) throws DAOException;
}
