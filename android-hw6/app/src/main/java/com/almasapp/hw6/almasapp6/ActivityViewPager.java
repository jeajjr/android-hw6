package com.almasapp.hw6.almasapp6;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ActivityViewPager extends ActionBarActivity implements FragmentRecyclerView.OnItemClickedListener {

    private ArrayList<Map<String, ?>> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        movieList = (new MovieData()).getMoviesList();

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), movieList);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(myFragmentPagerAdapter);

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float norm = Math.abs(Math.abs(position) - 1);
                page.setAlpha(norm);

                page.setScaleX(norm/2 + 0.5f);
                page.setScaleY(norm/2 + 0.5f);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(this, ActivityMovieDetail.class);
        i.putExtra("movie", movieList);
        i.putExtra("position", position);
        startActivity(i);
    }
}
