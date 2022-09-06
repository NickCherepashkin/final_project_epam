package com.drozdova.library.dao.impl;

import com.drozdova.library.bean.Book;
import com.drozdova.library.dao.BookDAO;
import com.drozdova.library.dao.ConnectionPoolException;
import com.drozdova.library.dao.DAOException;
import com.drozdova.library.dao.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLBookDAO implements BookDAO {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String BOOK_LIST_QUERY = "SELECT SQL_CALC_FOUND_ROWS book.*, genre.title AS 'genre', GROUP_CONCAT(author.name SEPARATOR' , ') as 'author' FROM book, genre, author, book_authors WHERE book.id_genre = genre.id and book.id = book_authors.id_book and book_authors.id_author = author.id AND book.delete_status = 1 GROUP BY id LIMIT ";
    private static final String NOVELTY_LIST_QUERY = "SELECT book.*, genre.title AS 'genre', GROUP_CONCAT(author.name SEPARATOR' , ') as 'author' FROM book, genre, author, book_authors WHERE book.id_genre = genre.id and book.id = book_authors.id_book and book_authors.id_author = author.id AND book.delete_status = 1 GROUP BY id ORDER BY id DESC LIMIT 6";
    private static final String EDIT_BOOK_INFO_QUERY = "UPDATE book SET title = ?, id_genre = ?, year = ?, pages = ?, language = ?, description = ? WHERE id = ?";
    private static final String UPDATE_BOOK_AUTHOR_QUERY = "UPDATE book_authors SET id_author = ? WHERE id_book = ?";
    private static final String EDIT_STATUS_BOOK_QUERY = "UPDATE book SET delete_status = 0 WHERE id = ?";
    private static final String ADD_BOOK_QUERY = "INSERT INTO book(title, id_genre, year, pages, language, description, delete_status) VALUES(?,?,?,?,?,?,1)";
    private static final String GET_BOOK_ID_QUERY = "SELECT id FROM book WHERE title = ?";
    private static final String INSERT_BOOK_AUTHOR_QUERY = "INSERT INTO book_authors(id_book, id_author) VALUES(?, ?)";
    private static final String GET_SORTED_BOOKS_LIST_QUERY = "SELECT SQL_CALC_FOUND_ROWS book.*, genre.title AS 'genre', GROUP_CONCAT(author.name SEPARATOR' , ') as 'author' FROM book, genre, author, book_authors WHERE book.id_genre = genre.id and book.id = book_authors.id_book and book_authors.id_author = author.id AND book.delete_status = 1 GROUP BY id ORDER BY ";
    private static final String FIND_BOOKS_QUERY = "SELECT SQL_CALC_FOUND_ROWS book.*, genre.title AS 'genre', GROUP_CONCAT(author.name SEPARATOR' , ') as 'author' FROM book, genre, author, book_authors WHERE book.id_genre = genre.id and book.id = book_authors.id_book and book_authors.id_author = author.id AND book.delete_status = 1 AND (book.title LIKE ? OR author.name LIKE ?)  GROUP BY id ORDER BY title ASC LIMIT ";
    private static final String COUNT_BOOKS_QUERY = "SELECT FOUND_ROWS();";

    private int noOfRecords;

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public List<Book> getBookList(int offset, int noOfRecords) throws DAOException {
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;

        List<Book> bookList;
        try {
            String query = BOOK_LIST_QUERY + offset + ", " + noOfRecords;
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            bookList = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setGenreTitle(resultSet.getString("genre"));
                book.setYear(resultSet.getInt("year"));
                book.setPages(resultSet.getInt("pages"));
                book.setLanguage(resultSet.getString("language"));
                book.setDescription(resultSet.getString("description"));

                bookList.add(book);
            }

            resultSet = statement.executeQuery(COUNT_BOOKS_QUERY);
            if (resultSet.next()) {
                this.noOfRecords = resultSet.getInt(1);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to get Book List", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a getBookList query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement, resultSet);
            }
        }
        return bookList;
    }

    @Override
    public List<Book> getNoveltyList() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<Book> noveltyList = null;

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(NOVELTY_LIST_QUERY);

            noveltyList = new ArrayList<>();
            String author;
            while(resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setGenreTitle(resultSet.getString("genre"));
                book.setYear(resultSet.getInt("year"));
                book.setPages(resultSet.getInt("pages"));
                book.setLanguage(resultSet.getString("language"));
                book.setDescription(resultSet.getString("description"));
                noveltyList.add(book);
            }

        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to get Novelty List", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a getNoveltyList query.", e);
        }finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement, resultSet);
            }
        }

        return noveltyList;
    }

    @Override
    public int editBookInfo(Book book, int idAuthor) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            int id = book.getId();
            if (id == 0) {
                pStatement = connection.prepareStatement(ADD_BOOK_QUERY);
            } else {
                pStatement = connection.prepareStatement(EDIT_BOOK_INFO_QUERY);
                pStatement.setInt(7, book.getId());
            }
            pStatement.setString(1, book.getTitle());
            pStatement.setInt(2, book.getIdGenre());
            pStatement.setInt(3, book.getYear());
            pStatement.setInt(4, book.getPages());
            pStatement.setString(5, book.getLanguage());
            pStatement.setString(6, book.getDescription());

            pStatement.executeUpdate();

            if (id == 0) {
                pStatement = connection.prepareStatement(GET_BOOK_ID_QUERY);
                pStatement.setString(1,book.getTitle());
                resultSet = pStatement.executeQuery();
                if(resultSet.next()) {
                    id = resultSet.getInt("id");
                }
                pStatement = connection.prepareStatement(INSERT_BOOK_AUTHOR_QUERY);
            } else {
                pStatement = connection.prepareStatement(UPDATE_BOOK_AUTHOR_QUERY);
            }

            pStatement.setInt(1, idAuthor);
            pStatement.setInt(2, id);

            pStatement.executeUpdate();

            return id;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to edit Book Info",e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make an editBookInfo query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, pStatement);
            }
        }
    }

    @Override
    public boolean deleteBook(int idBook) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;

        try {
            connection = connectionPool.takeConnection();
            pStatement = connection.prepareStatement(EDIT_STATUS_BOOK_QUERY);
            pStatement.setInt(1, idBook);
            pStatement.executeUpdate();
            return true;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to delete Book", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a deleteBook query", e);
        } finally {
            if(connection != null) {
                connectionPool.closeConnection(connection, pStatement);
            }
        }
    }

    @Override
    public List<Book> getSortedBooks(String param, int offset, int noOfRecords) throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<Book> bookList;

        try {
            String query = GET_SORTED_BOOKS_LIST_QUERY + param + " LIMIT " + offset + ", " + noOfRecords;
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            bookList = new ArrayList<>();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setGenreTitle(resultSet.getString("genre"));
                book.setYear(resultSet.getInt("year"));
                book.setPages(resultSet.getInt("pages"));
                book.setLanguage(resultSet.getString("language"));
                book.setDescription(resultSet.getString("description"));

                bookList.add(book);
            }

            resultSet = statement.executeQuery(COUNT_BOOKS_QUERY);
            if (resultSet.next()) {
                this.noOfRecords = resultSet.getInt(1);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to get Sorted Books", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a getSortedBooks query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement, resultSet);
            }
        }
        return bookList;
    }

    @Override
    public List<Book> findBooks(String param, int offset, int noOfRecords) throws DAOException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;

        List<Book> bookList;

        try {
            String newParam = "%" + param + "%";
            String query = FIND_BOOKS_QUERY + offset + ", " + noOfRecords;
            connection = connectionPool.takeConnection();
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, newParam);
            pStatement.setString(2, newParam);
            resultSet = pStatement.executeQuery();

            bookList = new ArrayList<>();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setGenreTitle(resultSet.getString("genre"));
                book.setYear(resultSet.getInt("year"));
                book.setPages(resultSet.getInt("pages"));
                book.setLanguage(resultSet.getString("language"));
                book.setDescription(resultSet.getString("description"));

                bookList.add(book);
            }

            resultSet = pStatement.executeQuery(COUNT_BOOKS_QUERY);
            if (resultSet.next()) {
                this.noOfRecords = resultSet.getInt(1);
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection Pool Error when trying to find Books", e);
        } catch (SQLException e) {
            throw new DAOException("Error when trying to make a findBooks query", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, pStatement, resultSet);
            }
        }
        return bookList;
    }
}