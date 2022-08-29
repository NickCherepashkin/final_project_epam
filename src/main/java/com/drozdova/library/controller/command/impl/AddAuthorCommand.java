package com.drozdova.library.controller.command.impl;

import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.AuthorService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import com.drozdova.library.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAuthorCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AddAuthorCommand.class);
    private static final AuthorService authorService = ServiceProvider.getInstance().getAuthorService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        String name = request.getParameter(ReqParam.AUTHOR);

        try {
            authorService.addAuthor(name);
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);
            request.removeAttribute(ReqParam.MESSAGE);
            request.removeAttribute(ReqParam.AUTHORS_LIST);
            request.getRequestDispatcher(JSPPageName.AUTHORS_PAGE).forward(request, response);
        } catch (ValidationException e) {
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_FAILED);
            request.setAttribute(ReqParam.MESSAGE, e.getMessage());
            try {
                request.getRequestDispatcher(JSPPageName.AUTHORS_PAGE).forward(request, response);
            } catch (IOException ex) {
                LOG.error("Invalid address to forward in the addAuthor command after validation.", ex);
                throw new ServletException(ex);
            }
        } catch (IOException e) {
            LOG.error("Invalid address to forward in the addAuthor command.", e);
            throw new ServletException(e);
        }
    }
}
