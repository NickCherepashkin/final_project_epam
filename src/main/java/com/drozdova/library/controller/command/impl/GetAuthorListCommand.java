package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.Author;
import com.drozdova.library.bean.Genre;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.AuthorService;
import com.drozdova.library.service.GenreService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAuthorListCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(GetAuthorListCommand.class);
    private static final AuthorService authorService = ServiceProvider.getInstance().getAuthorService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        List<Author> authorsList = authorService.getAuthorsList();
        request.setAttribute(ReqParam.AUTHORS_LIST, authorsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPageName.AUTHORS_PAGE);
        try {
            dispatcher.forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to forward in authors page", e);
            throw new ServletException(e);
        }
    }
}