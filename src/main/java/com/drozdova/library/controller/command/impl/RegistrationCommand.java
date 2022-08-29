package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.UserInfo;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
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

public class RegistrationCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegistrationCommand.class);
    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        String userName = request.getParameter(ReqParam.USERNAME);
        String userAddress = request.getParameter(ReqParam.ADDRESS);
        String userContact = request.getParameter(ReqParam.CONTACT);
        String userEmail = request.getParameter(ReqParam.EMAIL);
        String password = request.getParameter(ReqParam.PASSWORD);
        String confirmPassword = request.getParameter(ReqParam.CONFIRM_PASSWORD);
        int role;
        if (request.getSession().getAttribute(ReqParam.USER) == null) {
            role = 2;
        } else {
            role = Integer.parseInt(request.getParameter(ReqParam.ID_ROLE));
        }

        UserInfo userInfo = new UserInfo(userName, userAddress, userContact, userEmail);

        try {
            userService.registration(userInfo, password, confirmPassword, role);
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);
            request.removeAttribute(ReqParam.MESSAGE);

            if (request.getSession().getAttribute(ReqParam.USER) == null) {
                request.removeAttribute(ReqParam.USERINFO);
                request.getRequestDispatcher(JSPPageName.REGISTRATION_PAGE).forward(request, response);
            } else {
                request.setAttribute(ReqParam.MESSAGE, "Новый пользователь успешно добавлен.");
                request.getRequestDispatcher(JSPPageName.USERS_PAGE).forward(request, response);
            }
        } catch (ValidationException e) {
            request.setAttribute(ReqParam.USERINFO, userInfo);
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_FAILED);
            request.setAttribute(ReqParam.MESSAGE, e.getMessage());
            try {
                if (request.getSession().getAttribute(ReqParam.USER) == null) {
                    request.getRequestDispatcher(JSPPageName.REGISTRATION_PAGE).forward(request, response);
                } else {
                    request.getRequestDispatcher(JSPPageName.USERS_PAGE).forward(request, response);
                }
            } catch (IOException ex) {
                LOG.error("Invalid address to forward in the registration command after validation.", ex);
                throw new ServletException(ex);
            }
        } catch (IOException e) {
            LOG.error("Invalid address to forward in the registration command.", e);
            throw new ServletException(e);
        }
    }
}
