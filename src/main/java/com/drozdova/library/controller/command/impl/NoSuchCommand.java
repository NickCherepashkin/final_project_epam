package com.drozdova.library.controller.command.impl;

import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoSuchCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(NoSuchCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPageName.ERROR_PAGE);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            LOG.error("Invalid address getRequestDispatcher(/errorPage) in a nosuch command..", e);
        } catch (IOException e) {
            LOG.error("Error to forward in a noname command..", e);
        }
    }
}
