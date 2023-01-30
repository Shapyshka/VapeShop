package com.example.restik.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="message")
public class message implements Comparable<Object> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String text;

    private byte[] mediabytes;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "from_id")
    private user from;


    @ManyToOne
    @JoinColumn(name = "to_id")
    private user to;



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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public user getTo() {
        return to;
    }

    public void setTo(user to) {
        this.to = to;
    }

    public user getFrom() {
        return from;
    }
    public String getFromName() {
        return from.getUsername();
    }

    public void setFrom(user from) {
        this.from = from;
    }


    @Override
    public int compareTo(Object o) {
        message m = (message) o;
        return getDate().compareTo(((message)o).getDate());
    }
}