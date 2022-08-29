package com.drozdova.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private static final long serialVersionUID = 4092105916484405211L;
    private int id;
    private String title;
    private int idGenre;
    private String author;
    private String genreTitle;
    private int year;
    private int pages;
    private String language;
    private String description;

    public Book() {}

    public Book(int id, String title, int idGenre, String author, String genreTitle, int year, int pages, String language, String description) {
        this.id = id;
        this.title = title;
        //TODO idGenre скорее всего вообще не нужен !!! наверное лучше убрать
        this.idGenre = idGenre;
        this.author = author;
        this.genreTitle = genreTitle;
        this.year = year;
        this.pages = pages;
        this.language = language;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getGenreTitle() {
        return genreTitle;
    }

    public void setGenreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getId() == book.getId() && getIdGenre() == book.getIdGenre() && getYear() == book.getYear() && getPages() == book.getPages() && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getGenreTitle(), book.getGenreTitle()) && Objects.equals(getLanguage(), book.getLanguage()) && Objects.equals(getDescription(), book.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getIdGenre(), getAuthor(), getGenreTitle(), getYear(), getPages(), getLanguage(), getDescription());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", idGenre=" + idGenre +
                ", author='" + author + '\'' +
                ", genreTitle='" + genreTitle + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                ", language='" + language + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
