package com.example.restik.models;

import javax.persistence.*;

@Entity
@Table(name="cart")
public class cart {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private products product;

    private Integer quantity;

    private String status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private orders order;

    private String optIliRozn;

    private String colors;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public products getProduct() {
        return product;
    }

    public void setProduct(products product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public orders getOrder() {
        return order;
    }

    public void setOrder(orders order) {
        this.order = order;
    }

    public String getOptIliRozn() {
        return optIliRozn;
    }

    public void setOptIliRozn(String optIliRozn) {
        this.optIliRozn = optIliRozn;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }
}
