package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.User;
import com.drozdova.library.bean.UserInfo;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import com.drozdova.library.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUserInfoCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(UpdateUserInfoCommand.class);
    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        String idRole = request.getParameter(ReqParam.IDROLE);
        String userName = request.getParameter(ReqParam.USERNAME);
        String userAddress = request.getParameter(ReqParam.ADDRESS);
        String userContact = request.getParameter(ReqParam.CONTACT);
        String userEMail = request.getParameter(ReqParam.EMAIL);
        String pass = request.getParameter(ReqParam.PASSWORD);
        String confirmPass = request.getParameter(ReqParam.CONFIRM_PASSWORD);
        int id = Integer.parseInt(request.getParameter(ReqParam.ID));

        User user = (User) request.getSession().getAttribute(ReqParam.USER);

        UserInfo userInfo = new UserInfo(userName, userAddress, userContact, userEMail);
        userService.updateUserInfo(userInfo, id, pass, confirmPass);

        request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);
        request.getSession().setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);

        try {
            if (user.getIdRole() == 2) {
                request.setAttribute(ReqParam.USERINFO, userInfo);
                request.getRequestDispatcher(JSPPageName.USER_INFO_PAGE).forward(request, response);
            } else {
                request.getRequestDispatcher(JSPPageName.USERS_PAGE).forward(request, response);
            }

        } catch (IOException e) {
            LOG.error("Invalid address in getRequestDispatcher() in the updateUserInfo command..", e);
            throw new ServletException(e);
        }
    }
}
