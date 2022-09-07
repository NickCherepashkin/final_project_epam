package com.drozdova.library.controller.command.impl;

import com.drozdova.library.controller.JSPPageName;
import com.drozdova.library.controller.ReqParam;
import com.drozdova.library.controller.command.Command;
import com.drozdova.library.service.BookService;
import com.drozdova.library.service.ServiceException;
import com.drozdova.library.service.ServiceProvider;
import com.drozdova.library.service.validation.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBookCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(DeleteBookCommand.class);
    private static final BookService bookService = ServiceProvider.getInstance().getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        int idBook = Integer.parseInt(request.getParameter(ReqParam.ID_BOOK));
        try{
            bookService.deleteBook(idBook);
            request.getSession().setAttribute(ReqParam.STATUS, "delete_success");
            request.setAttribute(ReqParam.MESSAGE,"Книга была удалена." );
            request.getSession().removeAttribute(ReqParam.BOOKS_LIST);
            request.getRequestDispatcher(JSPPageName.BOOKS_PAGE).forward(request, response);
        } catch (IOException e) {
            LOG.error("Invalid address to forward in the deleteGenre command.", e);
            throw new ServletException(e);
        } catch (ValidationException e) {
            request.getSession().setAttribute(ReqParam.STATUS, "delete_failed");
            request.setAttribute(ReqParam.MESSAGE, e.getMessage());
            try {
                request.getRequestDispatcher(JSPPageName.BOOKS_PAGE).forward(request, response);
            } catch (IOException ex) {
                LOG.error("Invalid address to forward in the deleteGenre command after validation.", e);
                throw new ServletException(ex);
            }
        }
    }
}