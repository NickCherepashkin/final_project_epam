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

public class EditGenreCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditGenreCommand.class);
    private static final GenreService genreService = ServiceProvider.getInstance().getGenreService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        int id = Integer.parseInt(request.getParameter(ReqParam.ID));
        String title = request.getParameter(ReqParam.TITLE);

        try {
            genreService.editGenre(id, title);
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);
            request.setAttribute(ReqParam.MESSAGE, "Изменения сохранены");
            request.getRequestDispatcher(JSPPageName.GENRES_PAGE).forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address in getRequestDispatcher() in the editGenre command..", e);
            throw new ServletException(e);
        } catch (ValidationException e) {
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_FAILED);
            request.setAttribute(ReqParam.MESSAGE, e.getMessage());
            try {
                request.getRequestDispatcher(JSPPageName.GENRES_PAGE).forward(request, response);
            } catch (IOException ex) {
                LOG.error("Invalid address in getRequestDispatcher() in the editGenre command after validation..", e);
                throw new ServletException(ex);
            }
        }
    }
}