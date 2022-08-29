package com.drozdova.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {
    private static final long serialVersionUID = 5358829261410563535L;
    private int idOrder;
    private String fio;
    private int idBook;
    private String titleBook;
    private String author;
    private int idStatus;
    private String statusOrder;

    public Order() {
    }

    public Order(int idOrder, String fio, int idBook, String titleBook, String author, int idStatus, String statusOrder) {
        this.idOrder = idOrder;
        this.fio = fio;
        this.idBook = idBook;
        this.titleBook = titleBook;
        this.author = author;
        this.idStatus = idStatus;
        this.statusOrder = statusOrder;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getIdOrder() == order.getIdOrder() && getIdBook() == order.getIdBook() && getIdStatus() == order.getIdStatus() && Objects.equals(getFio(), order.getFio()) && Objects.equals(getTitleBook(), order.getTitleBook()) && Objects.equals(getAuthor(), order.getAuthor()) && Objects.equals(getStatusOrder(), order.getStatusOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrder(), getFio(), getIdBook(), getTitleBook(), getAuthor(), getIdStatus(), getStatusOrder());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "idOrder=" + idOrder +
                ", fio='" + fio + '\'' +
                ", idBook=" + idBook +
                ", titleBook='" + titleBook + '\'' +
                ", author='" + author + '\'' +
                ", idStatus=" + idStatus +
                ", statusOrder='" + statusOrder + '\'' +
                '}';
    }
}
