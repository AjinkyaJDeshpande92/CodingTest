package com.mindvalley.mindvalleyexercise.modules.details;


import com.mindvalley.mindvalleyexercise.entities.DetailsResponse;
import com.mindvalley.mindvalleyexercise.entities.UserDetailsResponse;

/**
 * This interface is used to declare the Base Activity Functions to be used/implemented by the Activity.
 *
 * @author AjinkyaD
 * @version 1.0
 */
interface DetailsView {

    void renderPhoto(String full);

    void setBackgroundColor(String color);

    void renderUserDetails(UserDetailsResponse user);

    void renderPhotoDetails(DetailsResponse currentData);

    void renderCategories(String displayCategories);
}
