package com.mindvalley.mindvalleyexercise.modules.listing;


import com.mindvalley.mindvalleyexercise.entities.DetailsResponse;
import com.mindvalley.mindvalleyexercise.modules.common.CommonView;

import java.util.ArrayList;

/**
 * This interface is used to declare the Base Activity Functions to be used/implemented by the Activity.
 *
 * @author AjinkyaD
 * @version 1.0
 */
interface ListingView extends CommonView{

    void renderUserList(ArrayList<DetailsResponse> users);
}
