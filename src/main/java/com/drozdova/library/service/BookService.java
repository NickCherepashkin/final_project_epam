package com.drozdova.library.service;

import com.drozdova.library.bean.Book;

import java.util.List;

public interface BookService {
    List<Book> getList() throws ServiceException;
    List<Book> getNoveltyList() throws ServiceException;
    int editBookInfo(Book book, int idAuthor) throws ServiceException;
    void deleteBook(int idBook) throws ServiceException;
    List<Book> getSortedBooks(String param) throws ServiceException;
    List<Book> findBooks(String param) throws ServiceException;
}
