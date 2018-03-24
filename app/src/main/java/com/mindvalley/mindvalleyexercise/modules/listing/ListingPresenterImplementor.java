package com.mindvalley.mindvalleyexercise.modules.listing;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindvalley.mindvalleyexercise.R;
import com.mindvalley.mindvalleyexercise.entities.DetailsResponse;
import com.mindvalley.mindvalleyexercise.utils.Utilities;
import com.mindvalley.mindvalleyexercise.utils.WebURL;
import com.mindvalley.webservicemanager.interfaces.WebserviceResponseListener;
import com.mindvalley.webservicemanager.models.WebCallDataTypeDownload;
import com.mindvalley.webservicemanager.models.WebCallDataTypeDownloadJson;
import com.mindvalley.webservicemanager.utilities.WebserviceManagerDataTypeDownload;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is used to initialise the Splash Activity UI Components and Objects.
 *
 * @author AjinkyaD
 * @version 1.0
 */
class ListingPresenterImplementor implements ListingPresenter {

    private ListingView listingView;
    private Context context;
    private WebserviceManagerDataTypeDownload mProvider;
    private ArrayList<DetailsResponse> users;

    ListingPresenterImplementor(Context context, ListingView listingView) {
        this.context = context;
        this.listingView = listingView;
        mProvider = WebserviceManagerDataTypeDownload.getInstance();
        users = new ArrayList<>();
    }

    @Override
    public void fetchUsers() {
        if (Utilities.isAirplaneModeWithNoWIFI(context)) {
            listingView.displayErrorMessage("");
            return;
        } else if (!Utilities.isNetworkAvailable(context)) {
            listingView.displayErrorMessage("");
            return;
        }

        fetchUsersFromServer();
    }

    @Override
    public void clearCache() {
        mProvider.clearTheCash();
        Toast.makeText(context, context.getResources().getString(R.string.cache_cleared), Toast.LENGTH_SHORT).show();
    }

    private void fetchUsersFromServer() {

        WebCallDataTypeDownload mDataTypeJson = new WebCallDataTypeDownloadJson(WebURL.URL, new WebserviceResponseListener() {
            @Override
            public void onStart(WebCallDataTypeDownload mDownloadDataType) {

            }

            @Override
            public void onSuccess(final WebCallDataTypeDownload mDownloadDataType) {
                String response = new String(mDownloadDataType.getData(), StandardCharsets.UTF_8);
                DetailsResponse[] detailsResponses = new Gson().fromJson(response, DetailsResponse[].class);

                if (detailsResponses.length != 0) {
                    users.clear();
                    Collections.addAll(users, detailsResponses);
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (listingView != null) {
                            listingView.renderUserList(users);
                        }
                    }
                }, context.getResources().getInteger(android.R.integer.config_mediumAnimTime));

            }

            @Override
            public void onFailure(WebCallDataTypeDownload mDownloadDataType, int statusCode, byte[] errorResponse, Throwable e) {
            }

            @Override
            public void onRetry(WebCallDataTypeDownload mDownloadDataType, int retryNo) {

            }
        });
        mProvider.getRequest(mDataTypeJson);
    }
}
