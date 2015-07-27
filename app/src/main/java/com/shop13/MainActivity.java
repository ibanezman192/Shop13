package com.shop13;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.shop13.adater.CustomListAdapter;
import com.shop13.app.AppController;
import com.shop13.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Products json url
    private static final String url = "http://www.shop13.gr/app_search.php";

    private ProgressDialog pDialog;
    private List<Product> productList = new ArrayList<Product>();
    /*private List<Product> caseList = new ArrayList<Product>();
    private List<Product> protectorList = new ArrayList<Product>();
    private List<Product> partsList = new ArrayList<Product>();
    private List<Product> chargeList = new ArrayList<Product>();*/
    String caseType="144", protectorType="176", partsType="174", chargeType="180";
    private ListView listView;
    private CustomListAdapter adapter;


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, productList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // changing action bar color
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1b1b1b")));

        // Creating volley request obj
        JsonArrayRequest productReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Product product = new Product();
                                product.setId(((Number) obj.get("id")).intValue());
                                try {
                                    product.setName(new String(obj.getString("name").getBytes("ISO-8859-7"), "UTF-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                product.setThumbnailUrl(obj.getString("img"));
                                //product.setRating(((Number) obj.get("rating")).doubleValue());
                                product.setPrice(obj.getString("price"));
                                product.setSiteUrl(obj.getString("url"));
                                product.setType(obj.getString("type"));

                                // Genre is json array
								/*JSONArray genreArry = obj.getJSONArray("genre");
								ArrayList<String> genre = new ArrayList<String>();
								for (int j = 0; j < genreArry.length(); j++) {
									genre.add((String) genreArry.get(j));
								}
								product.setGenre(genre);*/

                                // adding product to movies array
                                productList.add(product);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(productReq);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Tipota de kanei,einai gia test
        // update the main content by replacing fragments
        //Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        // Otan pataei o xristis to back button
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    // edw tha mpei toy nikou







}
