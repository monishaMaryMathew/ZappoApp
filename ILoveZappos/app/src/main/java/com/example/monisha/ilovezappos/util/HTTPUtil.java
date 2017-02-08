package com.example.monisha.ilovezappos.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Monisha on 2/5/2017.
 */

public class HTTPUtil {
    public JSONObject getCall(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response!=null ) {
                if(response.isSuccessful()) {
                    String responseString = response.body().string();
                    Log.d("TAG", "responseString : " + responseString);
                    return new JSONObject(responseString);
                } else {
                    Log.d("TAG", "Failure");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
