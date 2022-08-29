package com.drozdova.library.controller.command.impl;

import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        request.getSession().setAttribute(ReqParam.USER, null);
        RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPageName.HOME_PAGE);
        try {
            dispatcher.forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
