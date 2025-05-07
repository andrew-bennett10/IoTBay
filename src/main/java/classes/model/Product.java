package classes.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String description;
    private Integer stock;
    private Float price;
    private String supplier;

    public Product() {

    }

    public Product(int id, String name, String description, Integer stock, Float price, String supplier) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.supplier = supplier;
    }

    public Product(String name, String description, Integer stock, Float price, String supplier) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return this.stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getPrice() {
        return this.price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    public String getSupplier() {
        return this.supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

}