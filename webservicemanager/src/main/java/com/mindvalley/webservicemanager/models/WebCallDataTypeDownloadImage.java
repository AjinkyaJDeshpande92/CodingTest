package com.mindvalley.webservicemanager.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mindvalley.webservicemanager.interfaces.WebserviceResponseListener;


public class WebCallDataTypeDownloadImage extends WebCallDataTypeDownload {

    public WebCallDataTypeDownloadImage(String url, WebserviceResponseListener webserviceResponseListener) {
        super(url, WebCallDataType.IMAGE, webserviceResponseListener);
    }

    @Override
    public WebCallDataTypeDownload getCopyOfMe(WebserviceResponseListener webserviceResponseListener) {
        return new WebCallDataTypeDownloadImage(this.getUrl(), webserviceResponseListener);
    }

    public Bitmap getImageBitmap() {
        return BitmapFactory.decodeByteArray(getData(), 0, getData().length);
    }
}
