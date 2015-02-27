package com.almasapp.hw6.almasapp6;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentRecyclerView extends Fragment {
    static String TAG = "FragmentRecyclerView";
    static String ARG_MOVIE_LIST = "movie_list";
    static String ARG_LAYOUT = "layout";

    public static final int LAYOUT_LINEAR = 0;
    public static final int LAYOUT_GRID = 1;
    public static final int LAYOUT_STAGGERED_GRID = 2;

    private int layout;
    private ArrayList<Map<String, ?>> movieList;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private RecyclerView moviesRecyclerView;

    private OnItemClickedListener mListener;

    private ActionBarCallBack actionBarCallBack;

    public interface OnItemClickedListener {
        public void onItemClick(int position);
    }

    public FragmentRecyclerView() {
    }

    public static FragmentRecyclerView newInstance(ArrayList<Map<String, ?>> movieList, int layout) {
        FragmentRecyclerView fragment = new FragmentRecyclerView();

        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_LIST, movieList);
        args.putInt(ARG_LAYOUT, layout);
        fragment.setArguments(args);

        return fragment;
    }

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

                    int position = findInMovies(query);

                    if (position != -1)
                        moviesRecyclerView.scrollToPosition(position);
                    else
                        Toast.makeText(getActivity(), getResources().getString(R.string.recycler_view_not_found), Toast.LENGTH_SHORT).show();

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

    private int findInMovies (String search) {
        for (int index = 0; index < movieList.size(); index++)
            if (((String) movieList.get(index).get("name")).toLowerCase().contains(search.toLowerCase()))
                return index;

        return -1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movieList = (ArrayList<Map<String, ?>>) bundle.get(ARG_MOVIE_LIST);
            layout = bundle.getInt(ARG_LAYOUT);
        }
    }

    @Override
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

        moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        moviesRecyclerView.setHasFixedSize(true);

        switch (layout) {
            case LAYOUT_LINEAR:
                moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieList, LAYOUT_LINEAR);
                break;

            case LAYOUT_GRID:
                moviesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieList, LAYOUT_GRID);
                break;

            case LAYOUT_STAGGERED_GRID:
                moviesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieList, LAYOUT_STAGGERED_GRID);
                break;
        }

        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "list item clicked");

                mListener.onItemClick(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d(TAG, "list item long clicked");

                actionBarCallBack = new ActionBarCallBack(position);
                getActivity().startActionMode(actionBarCallBack);
            }
        });

        moviesRecyclerView.setAdapter(myRecyclerViewAdapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (actionBarCallBack != null)
            actionBarCallBack.finish();
        super.onDestroyView();
    }

    public class ActionBarCallBack implements ActionMode.Callback {
        int position;
        ActionMode mode;

        public ActionBarCallBack(int position) {
            this.position = position;
        }
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_or_popup_menu, menu);
            this.mode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            HashMap hm = (HashMap) movieList.get(position);
            mode.setTitle((String) hm.get("name"));
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            switch (id) {
                case R.id.item_delete:
                    movieList.remove(position);
                    myRecyclerViewAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;

                case R.id.item_duplicate:
                    movieList.add(position + 1, (HashMap) ((HashMap) movieList.get(position)).clone());
                    myRecyclerViewAdapter.notifyItemInserted(position + 1);
                    mode.finish();
                    break;
                default:
                    break;
            }
            return false;
        }

        public void finish() {
            mode.finish();
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}