package com.example.monisha.ilovezappos.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.monisha.ilovezappos.activity.ProductPageActivity;
import com.example.monisha.ilovezappos.util.HTTPUtil;

import org.json.JSONObject;

/**
 * Created by Monisha on 2/5/2017.
 */

public class ProductSearchCall extends AsyncTask {
    Activity activity;
    String query;
    JSONObject output = null;

    public ProductSearchCall(Activity activity,
                             String query) {
        this.activity = activity;
        this.query = query;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ((ProductPageActivity) activity).setProgressLayout(true);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        ((ProductPageActivity) activity).setFragment(2);
        ((ProductPageActivity) activity).setProgressLayout(false);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String url = "https://api.zappos.com/Search?term="+query+"&key=b743e26728e16b81da139182bb2094357c31d331";
        output = (new HTTPUtil()).getCall(url);
        if(output!= null){
            Log.d("TAG", "Success");
            ((ProductPageActivity) activity).setSearchResultModel(output);
        } else {
            Log.d("TAG", "Failure");
        }
        return null;
    }
}
