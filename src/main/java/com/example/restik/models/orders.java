package com.example.restik.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="orders")
public class orders {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;

    private String status;

    private String sposobpoluch;

    private String sposoboplaty;

    private String adress;

    private Integer zipcode;

    private Integer totalPrice;

    private Integer totalPostPrice;

    private double totalMass;

    private Date date;

    private String commentary;


    @ManyToOne
    @JoinColumn(name = "promo_id")
    private promos promo;


    private String fastphonenumber;

    private String fastemail;

    private String fastname;

    @ManyToOne
    @JoinColumn(name = "fasttovar_id")
    private products fasttovar;

    private Integer fastquan;

    private String fastmessen;

    public products getFasttovar() {
        return fasttovar;
    }

    public void setFasttovar(products fasttovar) {
        this.fasttovar = fasttovar;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.example.restik.models.user getUser() {
        return user;
    }

    public void setUser(com.example.restik.models.user user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSposobpoluch() {
        return sposobpoluch;
    }

    public void setSposobpoluch(String sposobpoluch) {
        this.sposobpoluch = sposobpoluch;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalPostPrice() {
        return totalPostPrice;
    }

    public void setTotalPostPrice(Integer totalPostPrice) {
        this.totalPostPrice = totalPostPrice;
    }

    public double getTotalMass() {
        return totalMass;
    }

    public void setTotalMass(double totalMass) {
        this.totalMass = totalMass;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getSposoboplaty() {
        return sposoboplaty;
    }

    public void setSposoboplaty(String sposoboplaty) {
        this.sposoboplaty = sposoboplaty;
    }

    public promos getPromo() {
        return promo;
    }

    public void setPromo(promos promo) {
        this.promo = promo;
    }




    public String getFastphonenumber() {
        return fastphonenumber;
    }

    public void setFastphonenumber(String fastphonenumber) {
        this.fastphonenumber = fastphonenumber;
    }

    public String getFastemail() {
        return fastemail;
    }

    public void setFastemail(String fastemail) {
        this.fastemail = fastemail;
    }


    public String getFastname() {
        return fastname;
    }

    public void setFastname(String fastname) {
        this.fastname = fastname;
    }

    public Integer getFastquan() {
        return fastquan;
    }

    public void setFastquan(Integer fastquan) {
        this.fastquan = fastquan;
    }

    public String getFastmessen() {
        return fastmessen;
    }

    public void setFastmessen(String fastmessen) {
        this.fastmessen = fastmessen;
    }
}
