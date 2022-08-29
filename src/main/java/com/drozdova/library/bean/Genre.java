package com.drozdova.library.bean;

import java.io.Serializable;
import java.util.Objects;

//TODO возможно вообще не понадобится данный класс, достаточно будет String
public class Genre implements Serializable {
    private static final long serialVersionUID = 1965377253079958773L;
    private int id;
    private String title;

    public Genre() {
    }

    public Genre(int id, String title) {
        this.id = id;
        this.title = title;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return getId() == genre.getId() && Objects.equals(getTitle(), genre.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
