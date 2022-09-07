package com.drozdova.library.controller.command.impl;

import com.drozdova.library.bean.Book;
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
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class EditBookInfoCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(EditBookInfoCommand.class);
    private static final BookService bookService = ServiceProvider.getInstance().getBookService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServiceException {
        try {

            String idStr = request.getParameter(ReqParam.ID);
            Book book = new Book();
            if (idStr == null) {
                book.setId(0);
            } else {
                book.setId(Integer.parseInt(request.getParameter(ReqParam.ID)));
            }

            book.setTitle(request.getParameter(ReqParam.TITLE));
            book.setIdGenre(Integer.parseInt(request.getParameter(ReqParam.ID_GENRE)));
            book.setYear(Integer.parseInt(request.getParameter(ReqParam.YEAR)));
            book.setPages(Integer.parseInt(request.getParameter(ReqParam.PAGES)));
            book.setLanguage(request.getParameter(ReqParam.LANGUAGE));
            book.setDescription(request.getParameter(ReqParam.DESCRIPTION));
            book.setCount(Integer.parseInt(request.getParameter(ReqParam.COUNT)));
            int idAuthor = Integer.parseInt(request.getParameter(ReqParam.ID_AUTHOR));
            int id = bookService.editBookInfo(book, idAuthor);
            String imgName = request.getParameter("file");

            if (imgName != null) {
                Part part = request.getPart(ReqParam.FILE);
                String path = request.getServletContext().getRealPath("\\") + "\\assets\\img\\books\\";
                File file = new File(path, id + ".png");
                if (file.exists()) {
                    file.delete();
                }

                try (InputStream input = part.getInputStream()) {
                    Files.copy(input, file.toPath());
                }
            }

            request.removeAttribute("bookList");
            request.getSession().setAttribute(ReqParam.STATUS, "edit_success");
            request.setAttribute(ReqParam.MESSAGE,(String)"Изменения сохранены");
            request.getRequestDispatcher(JSPPageName.BOOKS_PAGE).forward(request, response);
        } catch (ValidationException e) {
            request.getSession().setAttribute(ReqParam.STATUS, "edit_failed");
            request.setAttribute(ReqParam.MESSAGE, e.getMessage());
            try {
                request.getRequestDispatcher(JSPPageName.BOOKS_PAGE).forward(request, response);
            } catch (IOException ex) {
                LOG.error("Invalid address to forward in the editBookInfo command after validation.", e);
                throw new ServletException(ex);
            }
        } catch (IOException e) {
            LOG.error("Invalid address to forward in the editBookInfo command.", e);
            throw new ServletException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
