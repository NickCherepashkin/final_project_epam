package com.drozdova.library.service.impl;

import com.drozdova.library.bean.Book;
import com.drozdova.library.dao.BookDAO;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.DAOProvider;
import com.drozdova.library.service.BookService;
import com.drozdova.library.service.ServiceException;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static final BookDAO bookDAO = DAOProvider.getInstance().getBookDAO();

    @Override
    public List<Book> getList() throws ServiceException {
        try{
            return bookDAO.getBookList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getNoveltyList() throws ServiceException {
        try {
            return bookDAO.getNoveltyList();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int editBookInfo(Book book, int idAuthor) throws ServiceException {
        try {
            // TODO написать валидатор
            return bookDAO.editBookInfo(book, idAuthor);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBook(int idBook) throws ServiceException {
        try {
            bookDAO.deleteBook(idBook);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getSortedBooks(String param) throws ServiceException {
        try{
            return bookDAO.getSortedBooks(param);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> findBooks(String param) throws ServiceException {
        try{
            return bookDAO.findBooks(param);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
