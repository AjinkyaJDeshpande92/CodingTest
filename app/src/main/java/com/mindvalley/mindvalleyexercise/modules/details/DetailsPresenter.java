package com.mindvalley.mindvalleyexercise.modules.details;

import com.mindvalley.mindvalleyexercise.entities.DetailsResponse;

/**
 * This interface is used to declare the Splash Activity Functions to be used/implemented by the Presenter.
 *
 * @author AjinkyaD
 * @version 1.0
 */
interface DetailsPresenter {

    void renderDetails(DetailsResponse currentData);
}
