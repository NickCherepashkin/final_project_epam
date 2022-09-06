package com.drozdova.library.dao.impl;

import com.drozdova.library.bean.Order;
import com.drozdova.library.dao.ConnectionPoolException;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.OrderDAO;
import com.drozdova.library.dao.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLOrderDAO implements OrderDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String ADD_ORDER_QUERY = "INSERT INTO book_order(id_user, id_book, id_status) VALUES (?, ?, 1)";
    private static final String GET_ORDERS_BY_USER_QUERY = "SELECT book_order.id, book_order.id_book, user_info.fio AS 'fio', book.title, GROUP_CONCAT(author.name SEPARATOR' , ') AS 'name', book_order.id_status, status_order.status " +
            "FROM book_order, user_info, book, author, status_order, book_authors " +
            "WHERE book_order.id_user = ? AND book_order.id_user = user_info.id_user AND book_order.id_book = book.id " +
            "AND book_authors.id_book = book_order.id_book AND book_authors.id_author = author.id " +
            "AND book_order.id_status = status_order.id GROUP BY book.id ORDER BY ";
    private static final String GET_ORDERS_QUERY = "SELECT book_order.id, book_order.id_book, user_info.fio AS 'fio', book.title, author.name, book_order.id_status, status_order.status " +
            "FROM book_order, user_info, book, author, status_order, book_authors " +
            "WHERE book_order.id_user = user_info.id_user AND book_order.id_book = book.id " +
            "AND book_authors.id_book = book_order.id_book AND book_authors.id_author = author.id " +
            "AND book_order.id_status = status_order.id ORDER BY ";
    private static final String EDIT_ORDER_STATUS_QUERY = "UPDATE book_order SET id_status = ? WHERE id = ?";
    private static final String GET_ORDER_BY_USER_AND_BOOK = "SELECT id, id_status FROM book_order WHERE id_user = ? AND id_book = ?";

    @Override
    public boolean addOrder(int idUser, int idBook) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            pStatement = connection.prepareStatement(GET_ORDER_BY_USER_AND_BOOK);
            pStatement.setInt(1, idUser);
            pStatement.setInt(2, idBook);
            resultSet = pStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getInt("id_status") < 3) {
                    return false;
                }
            }

            pStatement = connection.prepareStatement(ADD_ORDER_QUERY);
            pStatement.setLong(1, idUser);
            pStatement.setInt(2, idBook);
            pStatement.executeUpdate();
            return true;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to add Order", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make an addOrder query", e);
        } finally {
            if(connection != null) {
                connectionPool.closeConnection(connection, pStatement);
            }
        }
    }

    @Override
    public List<Order> getOrdersList(String param, int idUser) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        List<Order> ordersList;

        try {

            connection = connectionPool.takeConnection();
            if(idUser == 0) {
                String query = GET_ORDERS_QUERY + param + " ASC";
                pStatement = connection.prepareStatement(query);
            } else {
                String query = GET_ORDERS_BY_USER_QUERY + param + " ASC";
                pStatement = connection.prepareStatement(query);
                pStatement.setLong(1, idUser);
            }

            resultSet = pStatement.executeQuery();

            ordersList = new ArrayList<>();

            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getInt("id"));
                order.setFio(resultSet.getString("fio"));
                order.setIdBook(resultSet.getInt("id_book"));
                order.setTitleBook(resultSet.getString("title"));
                order.setAuthor(resultSet.getString("name"));
                order.setIdStatus(resultSet.getInt("id_status"));
                order.setStatusOrder(resultSet.getString("status"));

                ordersList.add(order);
            }

            return ordersList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to get Orders List", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make an getOrdersList query",e);
        } finally {
            if(connection != null) {
                connectionPool.closeConnection(connection, pStatement, resultSet);
            }
        }
    }

    @Override
    public boolean editOrderStatus(int idOrder, int idStatus) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;

        try {
            connection = connectionPool.takeConnection();
            pStatement = connection.prepareStatement(EDIT_ORDER_STATUS_QUERY);
            pStatement.setInt(1, idStatus);
            pStatement.setInt(2, idOrder);
            pStatement.executeUpdate();

            return true;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to edit Order Status", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make an editOrderStatus query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, pStatement);
            }
        }
    }
}