package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.UserInfo;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import com.drozdova.library.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GetUserInfoCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(GetUserInfoCommand.class);
    private static final ServiceProvider provider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        UserService userService = provider.getUserService();
        int id = Integer.parseInt(request.getParameter(ReqParam.ID));
        UserInfo userInfo = userService.getUserInfo(id);
        request.setAttribute(ReqParam.USERINFO, userInfo);
        HttpSession session = request.getSession();
        session.removeAttribute(ReqParam.STATUS);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPageName.USER_INFO_PAGE);
        try {
            requestDispatcher.forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to redirect in userInfo page", e);
            throw new ServletException(e);
        }
    }
}
