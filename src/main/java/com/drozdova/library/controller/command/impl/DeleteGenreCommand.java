package com.drozdova.library.controller.command.impl;

import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.GenreService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import com.drozdova.library.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteGenreCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(DeleteGenreCommand.class);
    private static final GenreService genreService = ServiceProvider.getInstance().getGenreService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        int id = Integer.parseInt(request.getParameter(ReqParam.ID));
        String title = request.getParameter(ReqParam.TITLE);

        try {
            genreService.deleteGenre(id);
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);
            request.setAttribute(ReqParam.MESSAGE,"Жанр '" + title + "' успешно удален." );
            request.removeAttribute(ReqParam.GENRES_LIST);
            request.getRequestDispatcher(JSPPageName.GENRES_PAGE).forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to forward in the deleteGenre command.", e);
            throw new ServletException(e);
        }
    }
}
