package com.mindvalley.webservicemanager.utilities;

import com.loopj.android.http.*;
import com.mindvalley.webservicemanager.interfaces.WebserviceStatusListener;
import com.mindvalley.webservicemanager.models.WebCallDataTypeDownload;

import cz.msebera.android.httpclient.Header;


class WebCallSyncTaskManager {

    public AsyncHttpClient get(final WebCallDataTypeDownload webCallDataTypeDownload, final WebserviceStatusListener webserviceStatusListener) {
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(webCallDataTypeDownload.getUrl(), new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                webCallDataTypeDownload.getWebserviceResponseListener().onStart(webCallDataTypeDownload);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                webCallDataTypeDownload.setData(response);
                webCallDataTypeDownload.getWebserviceResponseListener().onSuccess(webCallDataTypeDownload);

                // Successfull response from server
                webserviceStatusListener.setDone(webCallDataTypeDownload);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                webCallDataTypeDownload.getWebserviceResponseListener().onFailure(webCallDataTypeDownload, statusCode, errorResponse, e);

                // Failure Response from server
                webserviceStatusListener.onFailure(webCallDataTypeDownload);
            }

            @Override
            public void onRetry(int retryNo) {
                // Retry the failed request
                webCallDataTypeDownload.getWebserviceResponseListener().onRetry(webCallDataTypeDownload, retryNo);
            }

            @Override
            public void onCancel() {
                super.onCancel();

                // Cancel the request
                webserviceStatusListener.setCancelled(webCallDataTypeDownload);
            }
        });

        return client;
    }
}


