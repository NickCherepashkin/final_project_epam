package com.drozdova.library.controller.command;

import com.drozdova.library.controller.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private static final Logger LOG = LogManager.getLogger(CommandProvider.class);
    private static final CommandProvider instance = new CommandProvider();
    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.GET_BOOK_LIST, new GetBookListCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.GET_USER_INFO, new GetUserInfoCommand());
        commands.put(CommandName.GET_NOVELTY_LIST, new GetNoveltyCommand());
        commands.put(CommandName.UPDATE_USER_INFO, new UpdateUserInfoCommand());
        commands.put(CommandName.GET_GENRES_LIST, new GetGenreListCommand());
        commands.put(CommandName.ADD_GENRE, new AddGenreCommand());
        commands.put(CommandName.DELETE_GENRE, new DeleteGenreCommand());
        commands.put(CommandName.EDIT_GENRE, new EditGenreCommand());
        commands.put(CommandName.GET_AUTHORS_LIST, new GetAuthorListCommand());
        commands.put(CommandName.ADD_AUTHOR, new AddAuthorCommand());
        commands.put(CommandName.DELETE_AUTHOR, new DeleteAuthorCommand());
        commands.put(CommandName.EDIT_AUTHOR, new EditAuthorCommand());
        commands.put(CommandName.ADD_ORDER, new AddOrderCommand());
        commands.put(CommandName.GET_ORDERS, new GetOrdersCommand());
        commands.put(CommandName.GET_ORDERS_BY_ID, new GetOrdersCommand());
        commands.put(CommandName.EDIT_ORDER_STATUS, new EditStatusOrderCommand());
        commands.put(CommandName.EDIT_BOOK_INFO, new EditBookInfoCommand());
        commands.put(CommandName.DELETE_BOOK, new DeleteBookCommand());
        commands.put(CommandName.ADD_BOOK, new EditBookInfoCommand());
        commands.put(CommandName.CHANGE_LANG, new ChangeLanguageCommand());
        commands.put(CommandName.GET_USERS_LIST, new GetUsersListCommand());
        commands.put(CommandName.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandName.SORTED_BOOKS, new SortedBooksCommand());
        commands.put(CommandName.FIND_BOOKS, new FindBooksCommand());
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Command getCommand(String commandName) {
        CommandName name = CommandName.valueOf(commandName.toUpperCase());
        Command command;
        if(null != name) {
            command = commands.get(name);
        } else {
            command = commands.get(CommandName.NO_SUCH_COMMAND);
            LOG.info("There is no command with that name.");
        }

        return command;
    }
}
