package com.mindvalley.webservicemanager.interfaces;


import com.mindvalley.webservicemanager.models.WebCallDataTypeDownload;

public interface WebserviceStatusListener {

    void setDone(WebCallDataTypeDownload webCallDataTypeDownload);

    void setCancelled(WebCallDataTypeDownload webCallDataTypeDownload);

    void onFailure(WebCallDataTypeDownload webCallDataTypeDownload);


}
