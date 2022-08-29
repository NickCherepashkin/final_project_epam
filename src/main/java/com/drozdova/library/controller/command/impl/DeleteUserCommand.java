package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.User;
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

public class DeleteUserCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(DeleteUserCommand.class);
    private static final UserService userService = ServiceProvider.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        int idForDel = Integer.parseInt(request.getParameter(ReqParam.IDUSER));
        int role = Integer.parseInt(request.getParameter(ReqParam.IDROLE));
        User user = (User) request.getSession().getAttribute(ReqParam.USER);
        String name = request.getParameter(ReqParam.USERNAME);
        int id = user.getId();
        try {
        int count = userService.deleteUser(id, idForDel, role);
            if (count > 0) {
                request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_FAILED);
                request.setAttribute(ReqParam.MESSAGE,"Пользователь еще не сдал " + count + " книг(и)." );
            } else {
                request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);
                request.setAttribute(ReqParam.MESSAGE,"Пользователь '" + name + "' успешно удален." );
                request.removeAttribute(ReqParam.USERS_LIST);
            }
            request.getRequestDispatcher(JSPPageName.USERS_PAGE).forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to forward in the deleteUser command after validation.", e);
            throw new ServletException(e);
        } catch (ValidationException e) {
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_FAILED);
            request.setAttribute(ReqParam.MESSAGE, e.getMessage() );
            try {
                request.getRequestDispatcher(JSPPageName.USERS_PAGE).forward(request, response);
            } catch (IOException ex) {
                LOG.error("Invalid address to forward in the deleteUser command after validation.", ex);
                throw new ServletException(ex);
            }
        }
    }
}
