package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.Book;
import com.drozdova.library.bean.Order;
import com.drozdova.library.bean.User;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.OrderService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetOrdersCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(GetOrdersCommand.class);
    private static final OrderService orderService = ServiceProvider.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        User user = (User) request.getSession().getAttribute(ReqParam.USER);
        int idRole = user.getIdRole();
        String param = request.getParameter(ReqParam.SORT);
        List<Order> ordersList;
        if (param == null) {
            param = ReqParam.ID;
        } else if (param.equals(ReqParam.STATUS)) {
            param = ReqParam.ID_STATUS;
        }
        if(idRole == 1) {
            ordersList = orderService.getOrdersList(param, 0);
        } else {
            ordersList = orderService.getOrdersList(param, user.getId());
        }


        request.setAttribute(ReqParam.ORDERS_LIST, ordersList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(JSPPageName.ORDERS_PAGE);
        try {
            requestDispatcher.forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to redirect in orders page", e);
            throw new ServletException(e);
        }
    }
}
