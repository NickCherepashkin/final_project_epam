package com.drozdova.library.dao.impl;

import com.drozdova.library.bean.Author;
import com.drozdova.library.dao.AuthorDAO;
import com.drozdova.library.dao.ConnectionPoolException;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLAuthorDAO implements AuthorDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String AUTHOR_LIST_QUERY = "SELECT id, name FROM author WHERE delete_status = 1 ORDER BY name ASC";
    private static final String GET_AUTHOR_QUERY = "SELECT id, delete_status FROM author WHERE author.name = ?";
    private static final String ADD_NEW_AUTHOR_QUERY = "INSERT INTO author(name, delete_status) VALUES(?, 1)";
    private static final String ADD_OLD_AUTHOR_QUERY = "UPDATE author SET delete_status = 1 WHERE name = ?";
    private static final String DELETE_AUTHOR_QUERY = "UPDATE author SET delete_status = 0 WHERE id = ?";
    private static final String EDIT_AUTHOR_QUERY = "UPDATE author SET name = ? WHERE id = ?";

    @Override
    public List<Author> getAuthorsList() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<Author> authorsList;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(AUTHOR_LIST_QUERY);

            authorsList = new ArrayList<>();

            while(resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt("id"));
                author.setName(resultSet.getString("name"));
                authorsList.add(author);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to get Authors List", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a getAuthorsList query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement);
            }
        }

        return authorsList;
    }

    @Override
    public boolean addAuthor(String name) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_AUTHOR_QUERY);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("delete_status") == 1) {
                    return false;
                } else {
                    preparedStatement = connection.prepareStatement(ADD_OLD_AUTHOR_QUERY);
                }
            } else {
                preparedStatement = connection.prepareStatement(ADD_NEW_AUTHOR_QUERY);

            }
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

            return true;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to add Author", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make an addAuthor query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public boolean deleteAuthor(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_AUTHOR_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            return true;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to delete Author", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a deleteAuthor query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public boolean editAuthor(int id, String name) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(EDIT_AUTHOR_QUERY);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            return true;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to edit Author", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make an editAuthor query", e);
        } finally {
            if (connection != null){
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }
}
