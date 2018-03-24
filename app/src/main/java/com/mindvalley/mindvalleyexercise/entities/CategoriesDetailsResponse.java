package com.mindvalley.mindvalleyexercise.entities;

import java.io.Serializable;

/**
 * Created by Ajinkya D on 23-03-2018.
 */

public class CategoriesDetailsResponse implements Serializable {


    /**
     * id : 4
     * title : Nature
     * photo_count : 46148
     * links : {"self":"https://api.unsplash.com/categories/4","photos":"https://api.unsplash.com/categories/4/photos"}
     */

    private int id;
    private String title;
    private int photo_count;
    private LinksDetailsResponse links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhoto_count() {
        return photo_count;
    }

    public void setPhoto_count(int photo_count) {
        this.photo_count = photo_count;
    }

    public LinksDetailsResponse getLinks() {
        return links;
    }

    public void setLinks(LinksDetailsResponse links) {
        this.links = links;
    }


}
