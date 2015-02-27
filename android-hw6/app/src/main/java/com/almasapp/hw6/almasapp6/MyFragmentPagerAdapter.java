package com.almasapp.hw6.almasapp6;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by José Ernesto on 18/02/2015.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    int count;
    private ArrayList<Map<String, ?>> movieData;

    public MyFragmentPagerAdapter (FragmentManager fm, ArrayList<Map<String, ?>> movieData) {
        super(fm);
        this.movieData = movieData;
        count = 3;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentRecyclerView.newInstance(movieData, position);
    }

    @Override
    public int getCount() {
        return count;
    }

    /**
     * Just for future customization.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        String name;

        switch (position) {
            case 0:
                name = "Home";
                break;
            case 1:
                name = "Most Popular";
                break;
            case 2:
                name = "Top Selling";
                break;
            default:
                name = "Home";
                break;
        }
        return name.toUpperCase(l);
    }
}