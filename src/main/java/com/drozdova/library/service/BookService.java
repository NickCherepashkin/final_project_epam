package com.drozdova.library.service;

import com.drozdova.library.bean.Book;

import java.util.List;

public interface BookService {
    List<Book> getList(int offset, int noOfRecords) throws ServiceException;
    int getNoOfRecords();
    List<Book> getNoveltyList() throws ServiceException;
    int editBookInfo(Book book, int idAuthor) throws ServiceException;
    void deleteBook(int idBook) throws ServiceException;
    List<Book> getSortedBooks(String param, int offset, int noOfRecords) throws ServiceException;
    List<Book> findBooks(String param, int offset, int noOfRecords) throws ServiceException;
}
