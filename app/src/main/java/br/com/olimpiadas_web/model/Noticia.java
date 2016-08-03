package br.com.olimpiadas_web.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bruno.santiago on 30/07/2016.
 */
public class Noticia implements Serializable,Comparable {

    @SerializedName("_id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("img")
    private String img;
    @SerializedName("body")
    private String body;
    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;
    @SerializedName("__v")
    private String stV;
    @SerializedName("pubDate")
    private Date pubDate;
    @SerializedName("createDate")
    private String createDate;
    @SerializedName("credit")
    private String credit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStV() {
        return stV;
    }

    public void setStV(String stV) {
        this.stV = stV;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public int compareTo(Object another) {
        if (((Noticia)another).getPubDate().after(pubDate)){
            return 0;
        }
        return -1;
    }
}
