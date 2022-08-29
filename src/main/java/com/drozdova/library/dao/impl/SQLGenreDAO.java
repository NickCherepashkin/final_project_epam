package com.drozdova.library.dao.impl;

import com.drozdova.library.bean.Genre;
import com.drozdova.library.dao.ConnectionPoolException;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.GenreDAO;
import com.drozdova.library.dao.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLGenreDAO implements GenreDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String GENRE_LIST_QUERY = "SELECT id, title FROM genre WHERE delete_status = 1 ORDER BY title ASC;";
    private static final String GET_GENRE_QUERY = "SELECT id, delete_status FROM genre WHERE genre.title = ?";
    private static final String ADD_NEW_GENRE_QUERY = "INSERT INTO genre(title, delete_status) VALUES(?, 1)";
    private static final String ADD_OLD_GENRE_QUERY = "UPDATE genre SET delete_status = 1 WHERE title = ?";
    private static final String DELETE_GENRE_QUERY = "UPDATE genre SET delete_status = 0 WHERE id = ?";
    private static final String EDIT_GENRE_QUERY = "UPDATE genre SET title = ? WHERE id = ?";

    @Override
    public List<Genre> getGenresList() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<Genre> genresList;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GENRE_LIST_QUERY);

            genresList = new ArrayList<>();

            while(resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getInt("id"));
                genre.setTitle(resultSet.getString("title"));
                genresList.add(genre);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to get Genres List", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a getGenresList query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement);
            }
        }

        return genresList;
    }

    @Override
    public boolean addGenre(String genre) throws DAOException {
        //TODO добавить поле status и отредактировать олгаритм
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_GENRE_QUERY);
            preparedStatement.setString(1, genre);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getInt("delete_status") == 1) {
                    return false;
                } else {
                    preparedStatement = connection.prepareStatement(ADD_OLD_GENRE_QUERY);
                }
            } else {
                preparedStatement = connection.prepareStatement(ADD_NEW_GENRE_QUERY);

            }
            preparedStatement.setString(1, genre);
            preparedStatement.executeUpdate();

            return true;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to addGenre", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make an addGenre query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public void deleteGenre(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_GENRE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to delete Genre", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a deleteGenre query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public boolean editGenre(int id, String title) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(EDIT_GENRE_QUERY);
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            return true;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to editGenre", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make an editGenre query", e);
        } finally {
            if (connection != null){
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }
}