package com.drozdova.library.controller.listener;

import com.drozdova.library.dao.ConnectionPoolException;
import com.drozdova.library.dao.connection.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            pool.initPoolData();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        pool.dispose();
    }
}
