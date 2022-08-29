package com.drozdova.library.controller.command.impl;

import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLanguageCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ChangeLanguageCommand.class);
    private static final String LANG = "language";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        HttpSession session = request.getSession(true);
        session.setAttribute(LANG, request.getParameter(LANG));
        try {
            request.getRequestDispatcher(JSPPageName.INDEX_PAGE).forward(request, response);
        } catch (IOException ex) {
            LOG.error("Invalid address to forward or redirect in the changeLanguage command..", ex);
            throw new ServletException(ex);
        }
    }
}
