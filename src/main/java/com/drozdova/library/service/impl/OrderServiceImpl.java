package com.drozdova.library.service.impl;

import com.drozdova.library.bean.Order;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.DAOProvider;
import com.drozdova.library.dao.OrderDAO;
import com.drozdova.library.service.OrderService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.validation.ValidationException;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final OrderDAO orderDAO = DAOProvider.getInstance().getOrderDAO();

    @Override
    public boolean addOrder(int idUser, int idBook) throws ServiceException {

        try {
            boolean result = orderDAO.addOrder(idUser, idBook);
            if (!result) {
                throw new ValidationException("Книга уже добавлена в заказы. Статус заказа можно посмотреть на странице 'Заказы'");
            }
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersList(String param, int idUser) throws ServiceException {
        try {
            return orderDAO.getOrdersList(param, idUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean editOrderStatus(int idOrder, int idStatus) throws ServiceException {
        try {
            orderDAO.editOrderStatus(idOrder, idStatus);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
