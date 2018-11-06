package com.example.nick.ptk;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by cc1057 on 1/29/18.
 * the class running the volly
 */

public class vollyhelper extends Application {


    private RequestQueue requestqueue;
    private static vollyhelper  instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized vollyhelper getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestqueue == null) {
            requestqueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestqueue ;
    }


    public <T> void addToRequestQueue(Request<T> request, String tag) {
        // set the default tag if tag is empty
        request.setTag(tag);
        getRequestQueue().add(request);
    }


    public void cancelPendingRequests(Object tag) {
        if (requestqueue != null) {
            requestqueue.cancelAll(tag);
        }
    }
}
