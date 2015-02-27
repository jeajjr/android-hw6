package com.almasapp.hw6.almasapp6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

public class FragmentMovieDetail extends Fragment {

    final String TAG = "MovieDetailFragment";
    static String ARG_SECTION_NUMBER = "args";
    static String ARG_COUNTER = "counter";

    int counter;

    private HashMap<String, ?> movie;

    public FragmentMovieDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            Log.d(TAG, "savedInstanceState null");
            counter = 0;
        }
        else {
            Log.d(TAG, "savedInstanceState not null");
            counter = savedInstanceState.getInt(ARG_COUNTER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_COUNTER, counter);
        Log.d(TAG, "saving instance");
    }

    public static FragmentMovieDetail newInstance(HashMap<String, ?> movie) {
        FragmentMovieDetail fragment = new FragmentMovieDetail();

        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_NUMBER, movie);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_movie_detail, menu);

        MenuItem shareItem = menu.findItem(R.id.movie_detail_share);
        ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, (String) movie.get("name"));
        actionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.movie_detail_share:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        final View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        movie = (HashMap<String, ?>) getArguments().get(ARG_SECTION_NUMBER);

        TextView title = (TextView) rootView.findViewById(R.id.textViewMovieDetailTitle);
        TextView year = (TextView) rootView.findViewById(R.id.textViewMovieDetailYear);
        TextView length = (TextView) rootView.findViewById(R.id.textViewMovieDetailLength);
        TextView stars = (TextView) rootView.findViewById(R.id.textViewMovieDetailStars);
        TextView director = (TextView) rootView.findViewById(R.id.textViewMovieDetailDirector);
        ImageView cover = (ImageView) rootView.findViewById(R.id.imageViewMovieDetailCover);
        RatingBar rating = (RatingBar) rootView.findViewById(R.id.ratingBarMovieDetailRating);
        TextView description = (TextView) rootView.findViewById(R.id.textViewMovieDetailDescription);

        title.setText(movie.get("name").toString());
        year.setText(movie.get("year").toString());
        length.setText(movie.get("length").toString());
        stars.setText(getResources().getText(R.string.movies_stars) + " " + movie.get("stars").toString());
        director.setText(getResources().getText(R.string.movies_director) + " " + movie.get("director").toString());
        cover.setImageResource((Integer) movie.get("image"));
        rating.setRating( (int) Double.parseDouble(movie.get("rating").toString()) + 1 );
        description.setText(movie.get("description").toString());

        return rootView;
    }
}