package com.mindvalley.mindvalleyexercise.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ajinkya D on 23-03-2018.
 */

public class UserDetailsResponse implements Serializable {
    /**
     * id : OevW4fja2No
     * username : nicholaskampouris
     * name : Nicholas Kampouris
     * profile_image : {"small":"https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702","medium":"https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64&s=ef631d113179b3137f911a05fea56d23","large":"https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128&s=622a88097cf6661f84cd8942d851d9a2"}
     * links : {"self":"https://api.unsplash.com/users/nicholaskampouris","html":"http://unsplash.com/@nicholaskampouris","photos":"https://api.unsplash.com/users/nicholaskampouris/photos","likes":"https://api.unsplash.com/users/nicholaskampouris/likes"}
     */

    private String id;
    private String username;
    private String name;
    private ProfileImageDetailsResponse profile_image;
    private LinksDetailsResponse links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileImageDetailsResponse getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(ProfileImageDetailsResponse profile_image) {
        this.profile_image = profile_image;
    }

    public LinksDetailsResponse getLinks() {
        return links;
    }

    public void setLinks(LinksDetailsResponse links) {
        this.links = links;
    }

}
