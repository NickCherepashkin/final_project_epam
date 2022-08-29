package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.Book;
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

public class  FindBooksCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(FindBooksCommand.class);
    private static final ServiceProvider provider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        String param = request.getParameter(ReqParam.FIND);

        BookService bookService = provider.getBookService();
        User user = (User) request.getSession().getAttribute(ReqParam.USER);
        List<Book> bookList = bookService.findBooks(param);
        request.setAttribute(ReqParam.BOOKS_LIST, bookList);
        RequestDispatcher requestDispatcher;
        if (user == null || user.getIdRole() == 2) {
            requestDispatcher = request.getRequestDispatcher(JSPPageName.CATALOG_PAGE);
        } else {
//            GenreService genreService = provider.getGenreService();
//            AuthorService authorService = provider.getAuthorService();
//            List<Genre> genresList = genreService.getGenresList();
//            List<Author> authorsList = authorService.getAuthorsList();
//            request.setAttribute("genresList", genresList);
//            request.setAttribute("authorsList", authorsList);
            requestDispatcher = request.getRequestDispatcher(JSPPageName.BOOKS_PAGE);
        }
        try {
            requestDispatcher.forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to redirect on page width find books", e);
            throw new ServletException(e);
        }
    }
}
