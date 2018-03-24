package com.onlineshop.entity;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Products")
public class Product  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message="is required")
    private String name;

    @Column(name = "short_description")
    @NotBlank(message="is required")
    private String shortDescription;

    @Column(name = "price")
    private float price;

    @Column(name = "image_path")
    @NotBlank(message="is required")
    private String imagePath;

    @Column(name = "description")
    @NotBlank(message="is required")
    private String description;

    @Column(name = "category")
    @NotBlank(message="is required")
    private String category;

    @Column(name="view")
    private String view;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
