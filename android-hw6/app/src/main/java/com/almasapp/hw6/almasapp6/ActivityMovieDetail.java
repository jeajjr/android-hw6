package com.almasapp.hw6.almasapp6;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ActivityMovieDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ArrayList<Map<String, ?>> movieList = (ArrayList<Map<String, ?>>) getIntent().getExtras().get("movie");
        int position = getIntent().getExtras().getInt("position");

        getSupportFragmentManager().beginTransaction()
            .add(R.id.container, FragmentMovieDetail.newInstance((HashMap<String, ?>) movieList.get(position)))
            .commit();
    }
}
