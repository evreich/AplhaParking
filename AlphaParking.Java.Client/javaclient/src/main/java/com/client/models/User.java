package com.client.models;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String login;
    private String password;
    private String fio;
    private String address;
    private String phone;
    private String email;

    public User(){}

    public User(int id, String login, String password, String fio, 
    String address, String phone, String email) {
        this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setFIO(fio);
        this.setAddress(address);
        this.setPhone(phone);
        this.setEmail(email);
    }

    public User(String login, String password, String fio, String address, 
    String phone, String email) {
        this.setLogin(login);
        this.setPassword(password);
        this.setFIO(fio);
        this.setAddress(address);
        this.setPhone(phone);
        this.setEmail(email);
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the fIO
     */
    public String getFIO() {
        return fio;
    }

    /**
     * @param fIO the fIO to set
     */
    public void setFIO(String fIO) {
        this.fio = fIO;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}