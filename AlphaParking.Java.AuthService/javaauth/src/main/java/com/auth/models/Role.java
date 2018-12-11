package com.auth.models;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;

    public Role(){}

    public Role(int id, String name) {
        this.setId(id);
        this.setName(name);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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