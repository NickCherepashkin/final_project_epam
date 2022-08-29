package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.Author;
import com.drozdova.library.bean.Book;
import com.drozdova.library.bean.Genre;
import com.drozdova.library.bean.User;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetBookListCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(GetBookListCommand.class);
    private static final ServiceProvider provider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        BookService bookService = provider.getBookService();
        User user = (User) request.getSession().getAttribute(ReqParam.USER);

        if (request.getSession().getAttribute(ReqParam.IS_VIEW) == null) {
            request.removeAttribute(ReqParam.PAGE);
            request.removeAttribute(ReqParam.NO_OF_PAGE);
            request.removeAttribute(ReqParam.CURRENT_PAGE);
            request.getSession().setAttribute(ReqParam.IS_VIEW, true);
            request.getSession().removeAttribute(ReqParam.IS_SORT);
            request.getSession().removeAttribute(ReqParam.IS_FIND);
            request.getSession().removeAttribute(ReqParam.FIND);
            request.getSession().removeAttribute(ReqParam.SORT);
        }
        try {
            int page = 1;
            int recordsPerPage = 3;
            if (request.getParameter(ReqParam.PAGE) != null) {
                page = Integer.parseInt(request.getParameter(ReqParam.PAGE));
            }

            List<Book> bookList = bookService.getList((page - 1)*recordsPerPage, recordsPerPage);

            int noOfRecords = bookService.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(ReqParam.BOOKS_LIST, bookList);
            request.setAttribute(ReqParam.NO_OF_PAGE, noOfPages);
            request.setAttribute(ReqParam.CURRENT_PAGE, page);
            RequestDispatcher requestDispatcher;
            if (user == null || user.getIdRole() == 2) {
                requestDispatcher = request.getRequestDispatcher(JSPPageName.CATALOG_PAGE);
            } else {
                GenreService genreService = provider.getGenreService();
                AuthorService authorService = provider.getAuthorService();
                List<Genre> genresList = genreService.getGenresList();
                List<Author> authorsList = authorService.getAuthorsList();
                request.setAttribute(ReqParam.GENRES_LIST, genresList);
                request.setAttribute(ReqParam.AUTHORS_LIST, authorsList);
                requestDispatcher = request.getRequestDispatcher(JSPPageName.BOOKS_PAGE);
            }
            requestDispatcher.forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to forward in books page", e);
            throw new ServletException(e);
        }
    }
}
