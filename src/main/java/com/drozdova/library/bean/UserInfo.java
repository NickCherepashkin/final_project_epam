package com.drozdova.library.bean;

import java.io.Serializable;
import java.util.Objects;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = -6081760926272557724L;
    private String name;
    private String address;
    private String contact;
    private String email;

    public UserInfo() {
    }

    public UserInfo(String name, String address, String contact, String email) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return name.equals(userInfo.name) && Objects.equals(address, userInfo.address) && email.equals(userInfo.email) && contact.equals(userInfo.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, email, contact);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
