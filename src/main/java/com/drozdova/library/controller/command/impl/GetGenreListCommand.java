package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.Genre;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
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

public class GetGenreListCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(GetGenreListCommand.class);
    private static final GenreService genreService = ServiceProvider.getInstance().getGenreService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        List<Genre> genresList = genreService.getGenresList();
        request.setAttribute(ReqParam.GENRES_LIST, genresList);
        RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPageName.GENRES_PAGE);
        try {
            dispatcher.forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to redirect in genres page", e);
            throw new ServletException(e);
        }
    }
}