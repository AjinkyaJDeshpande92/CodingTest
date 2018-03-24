package com.mindvalley.webservicemanager.utilities;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.mindvalley.webservicemanager.interfaces.WebserviceResponseListener;
import com.mindvalley.webservicemanager.interfaces.WebserviceStatusListener;
import com.mindvalley.webservicemanager.models.WebCallDataTypeDownload;

import java.util.HashMap;
import java.util.LinkedList;

public class WebserviceManagerDataTypeDownload {
    private static WebserviceManagerDataTypeDownload instance;
    private HashMap<String, LinkedList<WebCallDataTypeDownload>> allRequestsByKey = new HashMap<>();
    private HashMap<String, AsyncHttpClient> allRequestsClient = new HashMap<>();
    private WebServiceCachingManager webServiceCachingManager;

    private WebserviceManagerDataTypeDownload() {
        webServiceCachingManager = WebServiceCachingManager.getInstance();
    }

    public static WebserviceManagerDataTypeDownload getInstance() {
        if (instance == null) {
            instance = new WebserviceManagerDataTypeDownload();
        }
        return instance;
    }

    public void getRequest(final WebCallDataTypeDownload webCallDataTypeDownload) {
        final String mKey = webCallDataTypeDownload.getKeyMD5();
        // Check if exist in the cache
        WebCallDataTypeDownload webCallDataTypeDownloadFromCache = webServiceCachingManager.getMDownloadDataType(mKey);
        if (webCallDataTypeDownloadFromCache != null) {
            webCallDataTypeDownloadFromCache.comeFrom = "Cache";
            // call interface
            WebserviceResponseListener webserviceResponseListener = webCallDataTypeDownload.getWebserviceResponseListener();
            webserviceResponseListener.onStart(webCallDataTypeDownloadFromCache);
            webserviceResponseListener.onSuccess(webCallDataTypeDownloadFromCache);
            return;
        }

        // This will run if two request come for same url
        if (allRequestsByKey.containsKey(mKey)) {
            webCallDataTypeDownload.comeFrom = "Cache";
            allRequestsByKey.get(mKey).add(webCallDataTypeDownload);
            return;
        }

        // Put it in the allRequestsByKey to manage it after done or cancel
        if (allRequestsByKey.containsKey(mKey)) {
            allRequestsByKey.get(mKey).add(webCallDataTypeDownload);
        } else {
            LinkedList<WebCallDataTypeDownload> lstMDDataType = new LinkedList<>();
            lstMDDataType.add(webCallDataTypeDownload);
            allRequestsByKey.put(mKey, lstMDDataType);
        }

        // Create new WebCallDataTypeDownload by clone it from the parameter
        WebCallDataTypeDownload newWebCallDataTypeDownload = webCallDataTypeDownload.getCopyOfMe(new WebserviceResponseListener() {
            @Override
            public void onStart(WebCallDataTypeDownload mDownloadDataType) {
                for (WebCallDataTypeDownload m : allRequestsByKey.get(mDownloadDataType.getKeyMD5())) {
                    m.setData(mDownloadDataType.getData());
                    m.getWebserviceResponseListener().onStart(m);
                }
            }

            @Override
            public void onSuccess(WebCallDataTypeDownload mDownloadDataType) {
                for (WebCallDataTypeDownload m : allRequestsByKey.get(mDownloadDataType.getKeyMD5())) {
                    m.setData(mDownloadDataType.getData());
                    Log.e("HERRRR", m.comeFrom);
                    m.getWebserviceResponseListener().onSuccess(m);
                }
                allRequestsByKey.remove(mDownloadDataType.getKeyMD5());
            }

            @Override
            public void onFailure(WebCallDataTypeDownload mDownloadDataType, int statusCode, byte[] errorResponse, Throwable e) {
                for (WebCallDataTypeDownload m : allRequestsByKey.get(mDownloadDataType.getKeyMD5())) {
                    m.setData(mDownloadDataType.getData());
                    m.getWebserviceResponseListener().onFailure(m, statusCode, errorResponse, e);
                }
                allRequestsByKey.remove(mDownloadDataType.getKeyMD5());
            }

            @Override
            public void onRetry(WebCallDataTypeDownload mDownloadDataType, int retryNo) {
                for (WebCallDataTypeDownload m : allRequestsByKey.get(mDownloadDataType.getKeyMD5())) {
                    m.setData(mDownloadDataType.getData());
                    m.getWebserviceResponseListener().onRetry(m, retryNo);
                }
            }
        });

        // Get from download manager
        final WebCallSyncTaskManager webCallSyncTaskManager = new WebCallSyncTaskManager();
        AsyncHttpClient client = webCallSyncTaskManager.get(newWebCallDataTypeDownload, new WebserviceStatusListener() {
            @Override
            public void setDone(WebCallDataTypeDownload mDownloadDataType) {
                // put in the cache when mark as done
                webServiceCachingManager.putMDownloadDataType(mDownloadDataType.getKeyMD5(), mDownloadDataType);
                allRequestsClient.remove(mDownloadDataType.getKeyMD5());
            }

            @Override
            public void onFailure(WebCallDataTypeDownload mDownloadDataType) {
                allRequestsClient.remove(mDownloadDataType.getKeyMD5());
            }

            @Override
            public void setCancelled(WebCallDataTypeDownload mDownloadDataType) {
                allRequestsClient.remove(mDownloadDataType.getUrl());
            }
        });

        allRequestsClient.put(mKey, client);
    }

    public void cancelRequest(WebCallDataTypeDownload webCallDataTypeDownload) {
        if (allRequestsByKey.containsKey(webCallDataTypeDownload.getKeyMD5())) {
            allRequestsByKey.get(webCallDataTypeDownload.getKeyMD5()).remove(webCallDataTypeDownload);
            webCallDataTypeDownload.comeFrom = "cancelRequest";
            webCallDataTypeDownload.getWebserviceResponseListener().onFailure(webCallDataTypeDownload, 0, null, null);
        }
    }

    public boolean isQueueEmpty() {
        return allRequestsByKey.size() == 0;
    }

    public void clearTheCash() {
        webServiceCachingManager.clearTheCash();
    }
}
