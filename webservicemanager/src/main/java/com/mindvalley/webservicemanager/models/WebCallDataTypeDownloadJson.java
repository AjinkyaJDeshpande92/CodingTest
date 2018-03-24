package com.mindvalley.webservicemanager.models;

import com.google.gson.Gson;
import com.mindvalley.webservicemanager.interfaces.WebserviceResponseListener;

import java.lang.reflect.Type;


public class WebCallDataTypeDownloadJson extends WebCallDataTypeDownload {

    public WebCallDataTypeDownloadJson(String url, WebserviceResponseListener webserviceResponseListener) {
        super(url, WebCallDataType.JSON, webserviceResponseListener);
    }

    @Override
    public WebCallDataTypeDownload getCopyOfMe(WebserviceResponseListener webserviceResponseListener) {
        return new WebCallDataTypeDownloadJson(this.getUrl(), webserviceResponseListener);
    }

    private String getJsonAsString() {
        try {
            return new String(getData(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public Object getJson(Type type) {
        Gson gson = new Gson();
        return gson.fromJson(getJsonAsString(), type);
    }
}
