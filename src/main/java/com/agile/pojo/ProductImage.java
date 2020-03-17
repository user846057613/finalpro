package com.agile.pojo;

public class ProductImage {
    private Integer id;

    private Integer product_id;

    private String src;

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

    public String getSrc() { return src;}

    public void setSrc(String src) {this.src = src;}
}
