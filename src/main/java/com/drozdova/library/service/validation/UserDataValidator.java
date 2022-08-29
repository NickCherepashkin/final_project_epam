package com.drozdova.library.service.validation;

import com.drozdova.library.bean.UserInfo;

public class UserDataValidator{
    private static final String REG_EX_CHECK_EMAIL = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String REG_EX_CHECK_PHONE_NUMBER = ("^\\+\\d{10,14}$");

    private static final UserDataValidator instance = new UserDataValidator();

    private UserDataValidator() {}

    public boolean check(String login, String password) {
        return true;
    }

    public static UserDataValidator getInstance() {
        return instance;
    }

    public void validateUserInfo(UserInfo userInfo) throws ValidationException {
        if (userInfo == null) {
            throw new ValidationException("User is null");
        } else if (userInfo.getAddress() == null) {
            throw new ValidationException("Field 'Address' is empty.");
        } else if (userInfo.getContact() == null) {
            throw new ValidationException("Field 'Contact' is empty.");
        } else if (userInfo.getEmail() == null) {
            throw new ValidationException("Field 'Email' is empty.");
        }

        checkName(userInfo.getName());
        checkNumber(userInfo.getContact());
        checkEmail(userInfo.getEmail());
    }

    private void checkName(String name) throws ValidationException{
        if(name == null) {
            throw new ValidationException("Enter your name and surname.");
        }
    }

    public void checkEmail(String email) throws ValidationException{
        if(email != null && !email.matches(REG_EX_CHECK_EMAIL)) {
            throw new ValidationException("EMAIL is not currect.");
        }
    }
    private void checkNumber(String phone) throws ValidationException{
        if(phone != null && !phone.matches(REG_EX_CHECK_PHONE_NUMBER)) {
            //TODO исправить сообщение о неправильном номере
            throw new ValidationException("Phone number must consist of numbers. Format: +375111234567");
        }
    }

    public void checkPassword(String password, String confirmPassword) throws ValidationException{
        if(!password.equals(confirmPassword) && (!password.isEmpty() | !confirmPassword.isEmpty())) {
            throw new ValidationException("Пароли не совпадают.");
        }
    }

    public void isNotMyId(int idUser, int idForDel) throws ValidationException{
        if (idUser == idForDel) {
            throw new ValidationException("Вы не можете удалить сами себя.");
        }
    }
}
