package com.drozdova.library.service;

import com.drozdova.library.bean.Order;

import java.util.List;

public interface OrderService {
    boolean addOrder(int idUser, int idBook) throws ServiceException;
    List<Order> getOrdersList(String param, int idUser) throws ServiceException;
    boolean editOrderStatus(int idOrder, int idStatus) throws ServiceException;
}
