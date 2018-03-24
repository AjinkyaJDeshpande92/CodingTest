package com.mindvalley.mindvalleyexercise.modules.details;

import android.annotation.SuppressLint;

import com.mindvalley.mindvalleyexercise.entities.CategoriesDetailsResponse;
import com.mindvalley.mindvalleyexercise.entities.DetailsResponse;

import java.util.ArrayList;

/**
 * This class is used to initialise the Splash Activity UI Components and Objects.
 *
 * @author AjinkyaD
 * @version 1.0
 */
class DetailsPresenterImplementor implements DetailsPresenter {

    private DetailsView detailsView;

    DetailsPresenterImplementor(DetailsView detailsView) {
        this.detailsView = detailsView;
    }


    @Override
    public void renderDetails(DetailsResponse currentData) {

        if (detailsView != null) {
            if (currentData != null) {
                detailsView.renderPhoto(currentData.getUrls().getFull());
                detailsView.setBackgroundColor(currentData.getColor());
                detailsView.renderUserDetails(currentData.getUser());
                detailsView.renderPhotoDetails(currentData);
                detailsView.renderCategories(getDisplayCategories(currentData.getCategories()));
            }
        }

    }

    @SuppressLint("DefaultLocale")
    private String getDisplayCategories(ArrayList<CategoriesDetailsResponse> categories) {

        String displayCategories = "";

        for (CategoriesDetailsResponse categoriesDetailsResponse :
                categories) {
            if (displayCategories.trim().equals("")) {
                displayCategories = String.format("%s (%d)", categoriesDetailsResponse.getTitle(), categoriesDetailsResponse.getPhoto_count());
            } else {
                displayCategories = String.format("%s/%s", displayCategories, String.format("%s (%d)", categoriesDetailsResponse.getTitle(), categoriesDetailsResponse.getPhoto_count()));
            }

        }

        return displayCategories;


    }
}
