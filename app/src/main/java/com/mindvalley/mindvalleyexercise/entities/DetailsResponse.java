package com.mindvalley.mindvalleyexercise.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ajinkya D on 23-03-2018.
 */

public class DetailsResponse implements Serializable {

    /**
     * id : 4kQA1aQK8-Y
     * created_at : 2016-05-29T15:42:02-04:00
     * width : 2448
     * height : 1836
     * color : #060607
     * likes : 12
     * liked_by_user : false
     * user : {"id":"OevW4fja2No","username":"nicholaskampouris","name":"Nicholas Kampouris","profile_image":{"small":"https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702","medium":"https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64&s=ef631d113179b3137f911a05fea56d23","large":"https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128&s=622a88097cf6661f84cd8942d851d9a2"},"links":{"self":"https://api.unsplash.com/users/nicholaskampouris","html":"http://unsplash.com/@nicholaskampouris","photos":"https://api.unsplash.com/users/nicholaskampouris/photos","likes":"https://api.unsplash.com/users/nicholaskampouris/likes"}}
     * current_user_collections : []
     * urls : {"raw":"https://images.unsplash.com/photo-1464550883968-cec281c19761","full":"https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=4b142941bfd18159e2e4d166abcd0705","regular":"https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=1080&fit=max&s=1881cd689e10e5dca28839e68678f432","small":"https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=400&fit=max&s=d5682032c546a3520465f2965cde1cec","thumb":"https://images.unsplash.com/photo-1464550883968-cec281c19761?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max&s=9fba74be19d78b1aa2495c0200b9fbce"}
     * categories : [{"id":4,"title":"Nature","photo_count":46148,"links":{"self":"https://api.unsplash.com/categories/4","photos":"https://api.unsplash.com/categories/4/photos"}},{"id":6,"title":"People","photo_count":15513,"links":{"self":"https://api.unsplash.com/categories/6","photos":"https://api.unsplash.com/categories/6/photos"}}]
     * links : {"self":"https://api.unsplash.com/photos/4kQA1aQK8-Y","html":"http://unsplash.com/photos/4kQA1aQK8-Y","download":"http://unsplash.com/photos/4kQA1aQK8-Y/download"}
     */
    private String id;
    private String created_at;
    private int width;
    private int height;
    private String color;
    private int likes;
    private boolean liked_by_user;
    private UserDetailsResponse user;
    private UrlsDetailsResponse urls;
    private LinksDetailsResponse links;
    private ArrayList<?> current_user_collections;
    private ArrayList<CategoriesDetailsResponse> categories;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public UserDetailsResponse getUser() {
        return user;
    }

    public void setUser(UserDetailsResponse user) {
        this.user = user;
    }

    public UrlsDetailsResponse getUrls() {
        return urls;
    }

    public void setUrls(UrlsDetailsResponse urls) {
        this.urls = urls;
    }

    public LinksDetailsResponse getLinks() {
        return links;
    }

    public void setLinks(LinksDetailsResponse links) {
        this.links = links;
    }

    public ArrayList<?> getCurrent_user_collections() {
        return current_user_collections;
    }

    public void setCurrent_user_collections(ArrayList<?> current_user_collections) {
        this.current_user_collections = current_user_collections;
    }

    public ArrayList<CategoriesDetailsResponse> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoriesDetailsResponse> categories) {
        this.categories = categories;
    }
}
