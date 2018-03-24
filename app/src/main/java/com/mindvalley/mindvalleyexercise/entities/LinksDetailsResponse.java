package com.mindvalley.mindvalleyexercise.entities;

import java.io.Serializable;

/**
 * Created by Ajinkya D on 23-03-2018.
 */

public class LinksDetailsResponse implements Serializable {

    /**
     * self : https://api.unsplash.com/photos/4kQA1aQK8-Y
     * html : http://unsplash.com/photos/4kQA1aQK8-Y
     * download : http://unsplash.com/photos/4kQA1aQK8-Y/download
     * photos : https://api.unsplash.com/users/nicholaskampouris/photos
     * likes : https://api.unsplash.com/users/nicholaskampouris/likes
     */

    private String self;
    private String html;
    private String download;
    private String photos;
    private String likes;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
