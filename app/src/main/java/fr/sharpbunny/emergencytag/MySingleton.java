package fr.sharpbunny.emergencytag;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by 34011-14-02 on 04/05/2017.*
 */

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue RequestQueue;
    private  Context mCtx;

    private MySingleton(Context context) {
        mCtx = context;
        RequestQueue = getRequestQueue();

    }


    private RequestQueue getRequestQueue() {
        if (RequestQueue == null) {

            RequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return RequestQueue;
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

   }