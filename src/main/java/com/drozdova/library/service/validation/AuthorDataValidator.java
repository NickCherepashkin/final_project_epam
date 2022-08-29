package com.drozdova.library.service.validation;

public class AuthorDataValidator {
    private  static final AuthorDataValidator instance = new AuthorDataValidator();

    private AuthorDataValidator(){}

    public static AuthorDataValidator getInstance() {
        return instance;
    }

    public void checkName(String name) throws ValidationException{
        if(name == null || name.isEmpty()) {
            throw new ValidationException("Field with name is empty. Enter name, please.");
        }
    }
}
