package com.onlineshop.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private int orderId;

    @Column(name="username")
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_history")
    private DeliveryAddress deliveryAddress;

    @Column(name="total_price")
    private float totalPrice;

    @Column(name="order_date")
    private LocalDateTime orderDate;

    @Column(name="message")
    private String message;

    @Column(name="paid")
    private String paid;

    @Column(name="delivery")
    private String delivery;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Cart> orderedProducts;

    public Order() {
    }

    public Order(String username, DeliveryAddress deliveryAddress, float totalPrice, LocalDateTime orderDate, String message, String paid, String delivery) {
        this.username = username;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.message = message;
        this.paid = paid;
        this.delivery = delivery;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Cart> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<Cart> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
