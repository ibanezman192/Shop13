package com.shop13;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class About extends Fragment {

    private Menu activityMenu;
    private MenuItem curMenuItem;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.about,container,false);
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
