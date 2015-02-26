package com.almasapp.hw6.almasapp6;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentRecyclerView extends Fragment {

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