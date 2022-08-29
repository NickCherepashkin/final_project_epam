package com.drozdova.library.controller.command.impl;

import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.OrderService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import com.drozdova.library.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditStatusOrderCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditStatusOrderCommand.class);
    private static final OrderService orderService = ServiceProvider.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        int idOrder = Integer.parseInt(request.getParameter(ReqParam.ID_ORDER));
        int idStatus = Integer.parseInt(request.getParameter(ReqParam.ID_STATUS));

        try {
            orderService.editOrderStatus(idOrder, idStatus);
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_SUCCESS);
            request.setAttribute(ReqParam.MESSAGE, "Статус заказа №" + idOrder + " был изменен.");
            request.getRequestDispatcher(JSPPageName.ORDERS_PAGE).forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address in getRequestDispatcher() in the editOrderStatus command..", e);
            throw new ServletException(e);
        } catch (ValidationException e) {
            request.setAttribute(ReqParam.STATUS, ReqParam.STATUS_FAILED);
            request.setAttribute(ReqParam.MESSAGE, e.getMessage());
            try {
                request.getRequestDispatcher(JSPPageName.ORDERS_PAGE).forward(request, response);
            } catch (IOException ex) {
                LOG.error("Invalid address in getRequestDispatcher() in the editOrderStatus command after validation..", ex);
                throw new ServletException(ex);
            }
        }
    }
}
