package com.example.opel;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class My_Singleton {

    private static My_Singleton mInstance;
    private RequestQueue requestQueue;
    private final Context mCtx;
    private My_Singleton(Context Context)
    {
        mCtx = Context;
        requestQueue = getRequestQueue();
    }

    public static  synchronized My_Singleton getInstance (Context context)
    {
        if (mInstance==null)
        {
            mInstance =new My_Singleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;

    }

    public<T> void addToRequestQue(Request<T> request)
    {
        requestQueue.add(request);
    }
}
