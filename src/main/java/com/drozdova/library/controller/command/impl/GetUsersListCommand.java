package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.*;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetUsersListCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(GetUsersListCommand.class);
    private static final ServiceProvider provider = ServiceProvider.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        UserService userService = provider.getUserService();
        List<FullUserInfo> usersList = userService.getAllUsers();
        List<Role> rolesList = userService.getUsersRoles();
        request.setAttribute(ReqParam.USERS_LIST, usersList);
        request.setAttribute(ReqParam.ROLES_LIST, rolesList);
        RequestDispatcher requestDispatcher;

        requestDispatcher = request.getRequestDispatcher(JSPPageName.USERS_PAGE);
        try {
            requestDispatcher.forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to redirect in users page", e);
            throw new ServletException(e);
        }
    }
}
