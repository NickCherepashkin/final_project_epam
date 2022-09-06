package com.drozdova.library.dao;

import com.drozdova.library.bean.Order;

import java.util.List;

public interface OrderDAO {
    boolean addOrder(int idUser, int idBook) throws DAOException;
    List<Order> getOrdersList(String param, int idUser) throws DAOException;
    boolean editOrderStatus(int idOrder, int idStatus) throws DAOException;
}
