package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.Author;
import com.drozdova.library.bean.Book;
import com.drozdova.library.bean.Genre;
import com.drozdova.library.bean.User;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SortedBooksCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(SortedBooksCommand.class);
    private static final ServiceProvider provider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        String param = request.getParameter(ReqParam.SORT);
        if (request.getSession().getAttribute(ReqParam.IS_SORT) == null) {
            request.removeAttribute(ReqParam.PAGE);
            request.removeAttribute(ReqParam.NO_OF_PAGE);
            request.removeAttribute(ReqParam.CURRENT_PAGE);
            request.getSession().removeAttribute(ReqParam.IS_VIEW);
            request.getSession().setAttribute(ReqParam.IS_SORT, ReqParam.TRUE);
            request.getSession().removeAttribute(ReqParam.IS_FIND);
            request.getSession().removeAttribute(ReqParam.IS_FIND);
        }

        int page = 1;
        int recordsPerPage = 9;
        if (request.getParameter(ReqParam.PAGE) != null) {
            page = Integer.parseInt(request.getParameter(ReqParam.PAGE));
        }

        BookService bookService = provider.getBookService();
        User user = (User) request.getSession().getAttribute(ReqParam.USER);


        List<Book> bookList = bookService.getSortedBooks(param, (page - 1)*recordsPerPage, recordsPerPage);

        int noOfRecords = bookService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.getSession().setAttribute(ReqParam.BOOKS_LIST, bookList);
        request.setAttribute(ReqParam.NO_OF_PAGE, noOfPages);
        request.setAttribute(ReqParam.CURRENT_PAGE, page);
        request.getSession().setAttribute(ReqParam.SORT, param);
        RequestDispatcher requestDispatcher;
        if (user == null || user.getIdRole() == 2) {
            requestDispatcher = request.getRequestDispatcher(JSPPageName.CATALOG_PAGE);
        } else {
            GenreService genreService = provider.getGenreService();
            AuthorService authorService = provider.getAuthorService();
            List<Genre> genresList = genreService.getGenresList();
            List<Author> authorsList = authorService.getAuthorsList();
            request.setAttribute("genresList", genresList);
            request.setAttribute("authorsList", authorsList);
            requestDispatcher = request.getRequestDispatcher(JSPPageName.BOOKS_PAGE);
        }
        try {
            requestDispatcher.forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to redirect in sorted books page", e);
            throw new ServletException(e);
        }
    }
}
