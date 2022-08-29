package com.drozdova.library.dao.impl;

import com.drozdova.library.bean.FullUserInfo;
import com.drozdova.library.bean.Role;
import com.drozdova.library.bean.User;
import com.drozdova.library.bean.UserInfo;
import com.drozdova.library.dao.ConnectionPoolException;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.UserDAO;
import com.drozdova.library.dao.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAO implements UserDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger LOG = LogManager.getLogger(SQLUserDAO.class);

    private static final String USER_AUTHORISATION = "SELECT * FROM user WHERE login=? AND delete_status = 1";
    private static final String CHECK_USER_EXISTENCE_QUERY = "SELECT id FROM user WHERE login=? AND delete_status = 1";
    private static final String REGISTER_USER_INFO_QUERY = "INSERT INTO user_info(id_user, fio, address, email, contact) VALUES(?,?,?,?,?)";
    private static final String REGISTER_USER_QUERY = "INSERT INTO user(login, pass, id_role, delete_status) VALUES(?,?,?,1)";
    private static final String COUNT_USERS_QUERY = "SELECT COUNT(id) FROM user";
    private static final String USER_INFO_QUERY = "SELECT * FROM user_info WHERE id_user=?";
    private static final String UPDATE_USER_INFO = "UPDATE user_info SET fio = ?, address = ?, email = ?, contact = ? WHERE id_user = ?";
    private static final String UPDATE_USER = "UPDATE user SET login = ?, pass = ? WHERE id = ?";
    private static final String UPDATE_USER_WITHOUT_PASS = "UPDATE user SET login = ? WHERE id = ?";

    private static final String GET_ALL_USERS_QUERY = "SELECT user.id, user.id_role, user.pass, role.title, user_info.fio, user_info.address, user_info.email, user_info.contact" +
            " FROM user, role, user_info WHERE user.id = user_info.id_user AND user.id_role = role.id AND user.delete_status = 1";
    private static final String COUNT_ORDERS_BY_USER_QUERY = "SELECT COUNT(book_order.id) AS 'count' FROM book_order WHERE id_user = ? AND id_status = 2";
    private static final String DELETE_USER = "UPDATE user SET delete_status = 0 WHERE id = ?";
    private static final String GET_USERS_ROLES_QUERY = "SELECT * FROM role";

    @Override
    public User authorization(String login, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            pStatement = connection.prepareStatement(USER_AUTHORISATION);
            pStatement.setString(1, login);
            resultSet = pStatement.executeQuery();

            if(!resultSet.next()) {
                return new User();
            }

            if(!BCrypt.checkpw(password, resultSet.getString("pass"))) {
                return  new User();
            }

            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setLogin(resultSet.getString(2));
            user.setIdRole(resultSet.getInt(4));

            return user;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to authorization", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make an authorization query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, pStatement);
            }
        }
    }

    @Override
    public boolean registration(UserInfo userInfo, String password, int role) throws DAOException {
        // TODO исправить выполнение запросов с учетом хеширования пароля
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CHECK_USER_EXISTENCE_QUERY);
            preparedStatement.setString(1, userInfo.getEmail());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return false;
            }

            preparedStatement = connection.prepareStatement(REGISTER_USER_QUERY);
            preparedStatement.setString(1, userInfo.getEmail());
            preparedStatement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            preparedStatement.setInt(3, role);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                preparedStatement = connection.prepareStatement(COUNT_USERS_QUERY);
                resultSet = preparedStatement.executeQuery();
                int id = 0;
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }

                preparedStatement = connection.prepareStatement(REGISTER_USER_INFO_QUERY);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, userInfo.getName());
                preparedStatement.setString(3, userInfo.getAddress());
                preparedStatement.setString(4, userInfo.getEmail());
                preparedStatement.setString(5, userInfo.getContact());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a registration query", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to registration", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }

        return true;
    }

    @Override
    public UserInfo getUserInfo(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(USER_INFO_QUERY);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return  new UserInfo();
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setName(resultSet.getString(2));
            userInfo.setAddress(resultSet.getString(3));
            userInfo.setEmail(resultSet.getString(4));
            userInfo.setContact(resultSet.getString(5));

            return userInfo;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to get User Info", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a getUserInfo query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public void updateUserInfo(UserInfo userInfo, int id, String pass) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;

        try {
            connection = connectionPool.takeConnection();
            pStatement = connection.prepareStatement(UPDATE_USER_INFO);
            pStatement.setString(1, userInfo.getName());
            pStatement.setString(2, userInfo.getAddress());
            pStatement.setString(3, userInfo.getEmail());
            pStatement.setString(4, userInfo.getContact());
            pStatement.setInt(5, id);

            pStatement.executeUpdate();

            if (pass != null) {
                pStatement = connection.prepareStatement(UPDATE_USER);
                pStatement.setString(2, BCrypt.hashpw(pass, BCrypt.gensalt()));
                pStatement.setInt(3, id);
            } else {
                pStatement = connection.prepareStatement(UPDATE_USER_WITHOUT_PASS);
                pStatement.setInt(2, id);
            }
            pStatement.setString(1, userInfo.getEmail());
            pStatement.executeUpdate();

        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to update User Info", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a updateUserInfo query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, pStatement);
            }
        }
    }

    @Override
    public List<FullUserInfo> getAllUsers() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_USERS_QUERY);

            List<FullUserInfo> usersList = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User();
                UserInfo userInfo = new UserInfo();
                user.setId(resultSet.getInt("id"));
                user.setPassword(resultSet.getString("pass"));
                user.setIdRole(resultSet.getInt("id_role"));
                user.setRole(resultSet.getString("title"));
                userInfo.setName(resultSet.getString("fio"));
                userInfo.setAddress(resultSet.getString("address"));
                userInfo.setContact(resultSet.getString("contact"));
                userInfo.setEmail(resultSet.getString("email"));
                FullUserInfo fullUserInfo = new FullUserInfo(user, userInfo);

                usersList.add(fullUserInfo);
            }
            return usersList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to get All Users", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a getAllUsers query", e);
        } finally {
            if(connection != null) {
                connectionPool.closeConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public int deleteUser(int id, int role) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;

        try {
            int count = 0;
            connection = connectionPool.takeConnection();
            if (role == 2) {
                pStatement = connection.prepareStatement(COUNT_ORDERS_BY_USER_QUERY);
                pStatement.setInt(1, id);
                resultSet = pStatement.executeQuery();

                if (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
            if (count == 0) {
                pStatement = connection.prepareStatement(DELETE_USER);
                pStatement.setInt(1, id);
                pStatement.executeUpdate();
            }

            return count;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to delete User", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a deleteUser query", e);
        } finally {
            if(connection != null) {
                if (resultSet != null) {
                    connectionPool.closeConnection(connection, pStatement, resultSet);
                } else {
                    connectionPool.closeConnection(connection, pStatement);
                }
            }
        }
    }

    @Override
    public List<Role> getUsersRoles() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_USERS_ROLES_QUERY);

            List<Role> rolesList = new ArrayList<>();

            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setTitle(resultSet.getString("title"));

                rolesList.add(role);
            }

            return rolesList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to get Users Roles", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a getUsersRoles query", e);
        } finally {
            if(connection != null) {
                connectionPool.closeConnection(connection, statement, resultSet);
            }
        }
    }
}