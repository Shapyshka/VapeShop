package com.example.restik.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment")
public class comment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String text;

    private byte[] mediabytes;

    private Date date;

    private boolean rating;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private user author;

    @ManyToOne
    @JoinColumn(name = "zapis_id")
    private products zapis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getMediabytes() {
        return mediabytes;
    }

    public void setMediabytes(byte[] mediabytes) {
        this.mediabytes = mediabytes;
    }


    public Long getAuthorId() {

        return author.getId();
    }
    public String getAuthorName() {

        return author.getUsername();
    }

    public user getAuthor() {
        return author;
    }

    public void setAuthor(user author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public products getZapis() {
        return zapis;
    }

    public void setZapis(products zapis) {
        this.zapis = zapis;
    }

    public boolean getRating() {
        return rating;
    }

    public void setRating(boolean rating) {
        this.rating = rating;
    }
}