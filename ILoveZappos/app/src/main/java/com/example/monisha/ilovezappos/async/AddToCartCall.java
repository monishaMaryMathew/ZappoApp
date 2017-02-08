package com.example.monisha.ilovezappos.async;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.monisha.ilovezappos.R;
import com.example.monisha.ilovezappos.activity.ProductPageActivity;

/**
 * Created by Monisha on 2/7/2017.
 */

public class AddToCartCall extends AsyncTask {
    private Activity activity;

    public AddToCartCall(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ((ProductPageActivity) activity).fab.setVisibility(View.VISIBLE);
        ((ProductPageActivity) activity).fab_rings.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        //On success do the following
        ((ProductPageActivity) activity).fab.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.colorHighlight)));
        ((ProductPageActivity) activity).fab.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_add_shopping_cart_white_24dp));
        ((ProductPageActivity) activity).fab.setVisibility(View.VISIBLE);
        ((ProductPageActivity) activity).fab_rings.setVisibility(View.GONE);
        Toast.makeText(activity, "Added to Cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        //TODO Implement an addint to the cart API call
        //region sleep code
        try {
            Thread.sleep(3000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        //endregion sleep code
        return null;
    }
}
