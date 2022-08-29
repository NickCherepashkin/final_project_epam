package com.drozdova.library.controller;

import com.drozdova.library.controller.command.Command;
import com.drozdova.library.controller.command.CommandProvider;
import com.drozdova.library.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 11
)
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static CommandProvider provider = CommandProvider.getInstance();

    public Controller() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName;
        Command command;
        commandName = request.getParameter(ReqParam.COMMAND_NAME);
        command = provider.getCommand(commandName);
        try {
            command.execute(request, response);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
