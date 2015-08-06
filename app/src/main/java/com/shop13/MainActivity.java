package com.shop13;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.analytics.HitBuilders;
import com.shop13.DrawerActions.ProductFragment;
import com.shop13.UI.NavigationDrawerCallbacks;
import com.shop13.UI.NavigationDrawerFragment;
import com.shop13.app.AppController;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {

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

        setTitle(Build.MODEL + " | " + getString(R.string.action_no_filter)); //Vazei sto row to keimeno

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        //Fortnwnei me to kalosirthate to Products
        /*GoogleAnal.tracker.send(new HitBuilders.EventBuilder("ui", "open")
                .setCategory("Device")
                .setLabel(Build.MODEL)
                .build());*/
        /*GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        Tracker tracker = analytics.newTracker("UA-65982739-1"); // Send hits to tracker id UA-XXXX-Y*/

// All subsequent hits will be send with screen name = "main screen"
        /*tracker.setScreenName("main screen");*/
        AppController.tracker().send(new HitBuilders.EventBuilder()
                .setCategory("Device")
                .setAction(Build.MODEL)
                .build());
        ProductFragment fragment;
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new ProductFragment();
        fragmentManager.beginTransaction() //Ksekinaei to fragment pou dialextike
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Gyrnaei ti thesi pou epilextike
        // update the main content by replacing fragments
    }

    @Override
    public void onBackPressed() {
        // Otan pataei o xristis to back button kleinei to drawer
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
}