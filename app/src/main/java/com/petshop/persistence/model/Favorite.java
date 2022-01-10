package com.petshop.persistence.model;

public class Favorite {

    private Integer id;
    private Integer product_id;
    private Integer user_id;

    public Favorite() {
    }

    public Favorite(Integer id, Integer product_id, Integer user_id) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
