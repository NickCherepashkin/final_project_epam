package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.User;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import com.drozdova.library.service.UserService;
import com.drozdova.library.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AuthorizationCommand.class);
    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        String login;
        String password;

        login = request.getParameter(ReqParam.LOGIN);
        password = request.getParameter(ReqParam.PASSWORD);

        User user;
        String goToPage;
        try {
            user = userService.authorization(login, password);
            if (user.getLogin() == null) {
                LOG.info("Invalid username or password");
                request.getSession().setAttribute(ReqParam.STATUS, ReqParam.STATUS_FAILED);
                goToPage = JSPPageName.LOGIN_PAGE;
            } else {
                request.getSession().setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);
                goToPage = JSPPageName.HOME_PAGE;
            }

            request.getSession().setAttribute(ReqParam.USER, user);
            response.sendRedirect(goToPage);

        } catch (IOException e) {
            LOG.error("Invalid address to forward in the authorization command.", e);
            throw new ServletException(e);
        } catch (ValidationException e) {
            request.getSession().setAttribute(ReqParam.STATUS, ReqParam.STATUS_FAILED);
            try {
                request.getRequestDispatcher(JSPPageName.LOGIN_PAGE).forward(request, response);
            } catch (IOException ex) {
                LOG.error("Invalid address to forward in the authorization command after validation.", ex);
                throw new ServletException(ex);
            }
        }
    }
}
