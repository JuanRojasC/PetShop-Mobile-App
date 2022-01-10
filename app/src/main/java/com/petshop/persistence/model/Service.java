package com.petshop.persistence.model;

import java.io.Serializable;

public class Service implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String mode;
    private byte[] image;

    public Service() {
    }

    public Service(Integer id, String name, String description, Double price, String mode, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.mode = mode;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
