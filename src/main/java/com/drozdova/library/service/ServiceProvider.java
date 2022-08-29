package com.drozdova.library.service;

import com.drozdova.library.service.impl.*;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private UserService userService = new UserServiceImpl();
    private BookService bookService = new BookServiceImpl();
    private GenreService genreService = new GenreServiceImpl();
    private AuthorService authorService = new AuthorServiceImpl();
    private OrderService orderService = new OrderServiceImpl();

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public GenreService getGenreService() {
        return genreService;
    }

    public AuthorService getAuthorService() {
        return authorService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
