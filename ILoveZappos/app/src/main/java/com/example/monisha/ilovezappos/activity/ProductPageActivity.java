package com.example.monisha.ilovezappos.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.monisha.ilovezappos.R;
import com.example.monisha.ilovezappos.async.AddToCartCall;
import com.example.monisha.ilovezappos.async.ProductSearchCall;
import com.example.monisha.ilovezappos.fragments.ProductDetailsFragment;
import com.example.monisha.ilovezappos.fragments.StartPageFragment;
import com.example.monisha.ilovezappos.models.SearchResultModel;

import org.json.JSONObject;

public class ProductPageActivity extends AppCompatActivity implements StartPageFragment.OnStartPageFragmentInteractionListener,
        ProductDetailsFragment.OnProductDetailsFragmentInteractionListener {
    private SearchView searchView;
    private LinearLayout searchLayout;
    private RelativeLayout progressLayout;
    public SearchResultModel currSearchResultModel = null;
    private int currFragmentTag = 1; //Dedault

    public FloatingActionButton fab;
    public ProgressBar fab_rings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressLayout = (RelativeLayout) findViewById(R.id.progress_layout);

        //region SEARCH VIEW CODE
        searchView = (SearchView) findViewById(R.id.search_view);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
        //Set Text color
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorWhite));
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorWhite));
        // Sets searchable configuration defined in searchable.xml for this SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // When a user executes a search the system starts your searchable activity and sends it a ACTION_SEARCH intent
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
            Toast.makeText(this, "Query : " + query, Toast.LENGTH_LONG).show();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFor(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                filterSearchFor(query);
                return true;
            }
        });
        //endregion SEARCH VIEW CODE

        //region FAB elements initialization
        fab = (FloatingActionButton) findViewById(R.id.add_to_cart_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                addToCart(true);
            }
        });
        fab_rings = (ProgressBar) findViewById(R.id.add_to_cart_rings);
        setFABVisible(false);
        //endregion FAB elements initialization

        //region INITIALIZE FRAGMENT
        setFragment(1);//Default set StartPageFragment
        //endregion INITIALIZE FRAGMENT
    }

    private void filterSearchFor(String query) {
        //TODO implement this
    }

    private void searchFor(String query) {
        if(!query.isEmpty()) {
            initiateSearch(query);
            searchLayout.setVisibility(View.GONE);
        } else {
            Toast.makeText(ProductPageActivity.this, "Oops! Forgot to enter the search item?", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            //TODO Search Operations
            searchLayout.setVisibility(View.VISIBLE);

            return true;
        } else if (id == R.id.action_cart_summary) {
            //TODO Cart Summary Operations
            Toast.makeText(ProductPageActivity.this, "Feature coming soon", Toast.LENGTH_SHORT).show();
            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFragment(int fragmentId){
        addToCart(false);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        Fragment currentFragment = new StartPageFragment();
        currFragmentTag = fragmentId;
        switch (fragmentId) {
            case 1 :
                currentFragment = new StartPageFragment();
                break;
            case 2 :
                currentFragment = new ProductDetailsFragment();
                break;
            default:
                currentFragment = new StartPageFragment();
        }
        fragmentTransaction.replace(R.id.content_fragment_holder, currentFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStartPageFragmentInteraction(Uri uri) {
        //TODO
    }

    @Override
    public void onProductDetailsFragmentInteraction(Uri uri) {
        //TODO
    }

    @Override
    public void onBackPressed() {
        if(searchLayout.getVisibility()== View.VISIBLE) {
            searchLayout.setVisibility(View.GONE);
        } else if(currFragmentTag == 2) {
            setFragment(1);
        } else {
            super.onBackPressed();
        }
    }

    private void initiateSearch(String query) {
        (new ProductSearchCall(ProductPageActivity.this, query)).execute();
    }

    public void setProgressLayout(boolean set) {
        if (set) {
            progressLayout.setVisibility(View.VISIBLE);
        } else {
            progressLayout.setVisibility(View.GONE);
        }
    }

    public void setSearchResultModel(JSONObject jsonObject) {
        currSearchResultModel = new SearchResultModel();
        if(jsonObject!=null) {
            currSearchResultModel.setModelFromJSONObj(jsonObject);
        }
    }

    public void setFABVisible(boolean visible) {
        if(visible) {
            fab.setVisibility(View.VISIBLE);
            fab_rings.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.GONE);
            fab_rings.setVisibility(View.GONE);
        }
    }

    private void addToCart(boolean add) {
        if (add) {
            (new AddToCartCall(ProductPageActivity.this)).execute();
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_local_grocery_store_white_24dp));
            fab.setVisibility(View.GONE);
            fab_rings.setVisibility(View.GONE);
        }
    }
}
