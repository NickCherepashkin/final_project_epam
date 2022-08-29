package com.drozdova.library.service.validation;

public class GenreDataValidator {
    private  static final GenreDataValidator instance = new GenreDataValidator();

    private GenreDataValidator(){}

    public static GenreDataValidator getInstance() {
        return instance;
    }

    public void checkGenre(String genre) throws ValidationException{
        if(genre == null || genre.isEmpty()) {
            throw new ValidationException("Field with title of genre is empty. Enter title of genre.");
        }
    }
}
