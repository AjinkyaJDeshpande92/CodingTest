package com.mindvalley.webservicemanager.utilities;

import android.util.LruCache;

import com.mindvalley.webservicemanager.models.WebCallDataTypeDownload;


public class WebServiceCachingManager {
    private int maxCacheSize;
    private static WebServiceCachingManager instance;
    private LruCache<String, WebCallDataTypeDownload> mDownloadDataTypeCache;

    private WebServiceCachingManager() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        maxCacheSize = maxMemory / 8; // 4 * 1024 * 1024; // 4MiB
        mDownloadDataTypeCache = new LruCache<>(maxCacheSize);
    }

    public static WebServiceCachingManager getInstance() {
        if (instance == null) {
            instance = new WebServiceCachingManager();
        }
        return instance;
    }

    public WebCallDataTypeDownload getMDownloadDataType(String key) {
        return mDownloadDataTypeCache.get(key);
    }

    public boolean putMDownloadDataType(String key, WebCallDataTypeDownload webCallDataTypeDownload) {
        return mDownloadDataTypeCache.put(key, webCallDataTypeDownload) != null;
    }

    public void clearTheCash() {
        mDownloadDataTypeCache.evictAll();
    }
}
