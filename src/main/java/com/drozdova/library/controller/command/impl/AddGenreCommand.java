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

public class AddGenreCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddGenreCommand.class);
    private static final GenreService genreService = ServiceProvider.getInstance().getGenreService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        String genre = request.getParameter(ReqParam.GENRE);

        try {
            genreService.addGenre(genre);
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);
            request.setAttribute(ReqParam.MESSAGE, "Жанр '" + genre + "' успешно добавлен.");
            request.removeAttribute(ReqParam.GENRES_LIST);
            request.getRequestDispatcher(JSPPageName.GENRES_PAGE).forward(request, response);
        } catch (ValidationException e) {
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_FAILED);
            request.setAttribute(ReqParam.MESSAGE, e.getMessage());
            try {
                request.getRequestDispatcher(JSPPageName.GENRES_PAGE).forward(request, response);
            } catch (IOException ex) {
                LOG.error("Invalid address to forward in the addGenre command after validation.", ex);
                throw new ServletException(ex);
            }
        } catch (IOException e) {
            LOG.error("Invalid address to forward in the addGenre command.", e);
            throw new ServletException(e);
        }
    }
}
