package com.example.restik.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="products")
public class products {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

//    @NotEmpty(message = "Заполните название")
//    @Size(min=2,max=40,message = "Длина строки должна находиться в диапозоне от 2 до 40 символов")
    private String title;

    private String text;

    private Integer price;

    private Integer mass;
    private Integer volume;
    private Integer nicotine;

    private Integer oldprice;

    private Integer quantity;

    private Date date;

    private String photoUrl;

    private String typeofproduct;

    private String optIliRozn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getMass() {
        return mass;
    }

    public void setMass(Integer mass) {
        this.mass = mass;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getOldprice() {
        return oldprice;
    }

    public void setOldprice(Integer oldprice) {
        this.oldprice = oldprice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTypeofproduct() {
        return typeofproduct;
    }

    public void setTypeofproduct(String typeofproduct) {
        this.typeofproduct = typeofproduct;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getNicotine() {
        return nicotine;
    }

    public void setNicotine(Integer nicotine) {
        this.nicotine = nicotine;
    }

    public String getOptIliRozn() {
        return optIliRozn;
    }

    public void setOptIliRozn(String optIliRozn) {
        this.optIliRozn = optIliRozn;
    }
}