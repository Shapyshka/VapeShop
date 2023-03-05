package com.example.restik.models;
import javax.persistence.*;

@Entity
@Table(name="news")
public class news {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String imgurl;

    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
