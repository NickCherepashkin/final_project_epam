package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.Book;
import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.BookService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetNoveltyCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(GetNoveltyCommand.class);
    private static final BookService bookService = ServiceProvider.getInstance().getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        List<Book> noveltyList = bookService.getNoveltyList();
        request.setAttribute(ReqParam.NOVELTIES_LIST, noveltyList);
        RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPageName.HOME_PAGE);
        try {
            dispatcher.forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to redirect in home page", e);
            throw new ServletException(e);
        }
    }
}
