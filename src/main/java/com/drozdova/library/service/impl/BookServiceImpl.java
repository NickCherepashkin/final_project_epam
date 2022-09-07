package com.drozdova.library.service.impl;

import com.drozdova.library.bean.Book;
import com.drozdova.library.dao.BookDAO;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.DAOProvider;
import com.drozdova.library.service.BookService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.validation.ValidationException;

import java.util.List;

public class BookServiceImpl implements BookService {
    private static final BookDAO bookDAO = DAOProvider.getInstance().getBookDAO();

    @Override
    public List<Book> getList(int offset, int noOfRecords) throws ServiceException {
        try{
            return bookDAO.getBookList(offset, noOfRecords);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getNoOfRecords() {
        return bookDAO.getNoOfRecords();
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
            int result = bookDAO.editBookInfo(book, idAuthor);
            if (result == 0) {
                throw new ValidationException("Данная книга уже добавлена");
            }
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBook(int idBook) throws ServiceException {
        try {
            if (!bookDAO.deleteBook(idBook)) {
                throw new ValidationException("Не все экземпляры книги возвращены читателями");
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> getSortedBooks(String param, int offset, int noOfRecords) throws ServiceException {
        try{
            return bookDAO.getSortedBooks(param, offset, noOfRecords);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Book> findBooks(String param, int offset, int noOfRecords) throws ServiceException {
        try{
            return bookDAO.findBooks(param, offset, noOfRecords);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
