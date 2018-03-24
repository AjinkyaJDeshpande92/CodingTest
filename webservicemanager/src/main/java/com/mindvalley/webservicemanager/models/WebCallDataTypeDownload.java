package com.mindvalley.webservicemanager.models;


import com.mindvalley.webservicemanager.interfaces.WebserviceResponseListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public abstract class WebCallDataTypeDownload {
    private String url;
    private byte[] data;
    private WebCallDataType webCallDataType;
    private WebserviceResponseListener webserviceResponseListener;

    private String keyMD5;

    // User For Just For Test
    public String comeFrom = "Internet";

    protected WebCallDataTypeDownload(String url, WebCallDataType webCallDataType, WebserviceResponseListener webserviceResponseListener) {
        this.url = url;
        this.webCallDataType = webCallDataType;
        this.webserviceResponseListener = webserviceResponseListener;
        this.keyMD5 = md5(this.url);
    }

    public String getKeyMD5() {
        return keyMD5;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getData() {
        return data;
    }

    public WebCallDataType getWebCallDataType() {
        return webCallDataType;
    }

    public WebserviceResponseListener getWebserviceResponseListener() {
        return webserviceResponseListener;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public abstract WebCallDataTypeDownload getCopyOfMe(WebserviceResponseListener webserviceResponseListener);


    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
