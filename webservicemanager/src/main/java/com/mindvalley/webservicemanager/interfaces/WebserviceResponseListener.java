package com.mindvalley.webservicemanager.interfaces;

import com.mindvalley.webservicemanager.models.WebCallDataTypeDownload;

public interface WebserviceResponseListener {

    void onStart(WebCallDataTypeDownload webCallDataTypeDownload);

    void onSuccess(WebCallDataTypeDownload webCallDataTypeDownload);

    void onFailure(WebCallDataTypeDownload webCallDataTypeDownload, int statusCode, byte[] errorResponse, Throwable e);

    void onRetry(WebCallDataTypeDownload webCallDataTypeDownload, int retryNo);
}
