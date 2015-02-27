package com.almasapp.hw6.almasapp6;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityRecyclerView extends ActionBarActivity implements FragmentRecyclerView.OnItemClickedListener {
    private ArrayList<Map<String, ?>> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        if (savedInstanceState == null) {
            movieList = (new MovieData()).getMoviesList();

            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, FragmentRecyclerView.newInstance(movieList, FragmentRecyclerView.LAYOUT_LINEAR))
                .commit();
        }

        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FragmentMovieDetail.newInstance((HashMap<String,?>) movieList.get(position)))
                .addToBackStack(null)
                .commit();
    }
}
