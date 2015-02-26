package com.almasapp.hw6.almasapp6;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class FragmentRecyclerView extends Fragment {
    static String TAG = "FragmentRecyclerView";

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu");

        if (menu.findItem(R.id.action_search) == null) {
            inflater.inflate(R.menu.menu_fragment_recycler_view, menu);
            Log.d(TAG, "inflating menu");
        }
        else
            Log.d(TAG, "inflating not necessary");

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (searchView != null) {
            Log.d(TAG, "setting OnQueryTextListener");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query){
                    Log.d(TAG, "onQueryTextSubmit");
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String query){
                    Log.d(TAG, "onQueryTextChange");
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    MovieData movieData;
    private OnItemClickedListener mListener;

    public interface OnItemClickedListener {
        public void onItemClick(int position);
    }

    public FragmentRecyclerView() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        movieData = new MovieData();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnItemClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemClickedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        setHasOptionsMenu(true);

        RecyclerView moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set adapter
        final MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList());

        myRecyclerViewAdapter.SetOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                mListener.onItemClick(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

        });

        moviesRecyclerView.setAdapter(myRecyclerViewAdapter);

        return rootView;
    }
}