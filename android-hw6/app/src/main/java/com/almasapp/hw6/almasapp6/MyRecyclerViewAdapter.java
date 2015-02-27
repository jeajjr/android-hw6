package com.almasapp.hw6.almasapp6;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by José Ernesto on 12/02/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private static String TAG = "MyRecyclerViewAdapter";

    private List<Map<String, ?>> mDataSet;
    private Context context;
    private int layout;
    OnMovieMenuClickListener onMovieMenuClickListener;
    OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<Map<String, ?>> mDataSet, int layout) {
        this.mDataSet = mDataSet;
        this.context = context;

        if (!(layout == FragmentRecyclerView.LAYOUT_LINEAR ||
                layout == FragmentRecyclerView.LAYOUT_GRID ||
                layout == FragmentRecyclerView.LAYOUT_STAGGERED_GRID ))
            throw new IllegalArgumentException("Layout selected not valid");

        this.layout = layout;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public ImageButton menuButton;
        public RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            switch (layout) {
                case FragmentRecyclerView.LAYOUT_LINEAR:
                    vIcon = (ImageView) v.findViewById(R.id.imageViewMoviesImage);
                    vTitle = (TextView) v.findViewById(R.id.textViewMoviesTitle);
                    vDescription = (TextView) v.findViewById(R.id.textViewMoviesDescription);
                    menuButton = (ImageButton) v.findViewById(R.id.imageButtonViewMovieMenu);
                    ratingBar = (RatingBar) v.findViewById(R.id.ratingBarMovie);

                    menuButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onMovieMenuClickListener != null) {
                                onMovieMenuClickListener.onItemClick(v, getPosition());
                            }
                        }
                    });
                    break;

                case FragmentRecyclerView.LAYOUT_GRID:
                    vIcon = (ImageView) v.findViewById(R.id.imageViewMoviesImage);
                    break;

                case FragmentRecyclerView.LAYOUT_STAGGERED_GRID:
                    vIcon = (ImageView) v.findViewById(R.id.imageViewMoviesImage);
                    vTitle = (TextView) v.findViewById(R.id.textViewMoviesTitle);
                    vDescription = (TextView) v.findViewById(R.id.textViewMoviesDescription);
                    break;
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, getPosition());
                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemLongClick(v, getPosition());
                    }
                    return true;
                }
            });
        }

        public void bindMovieData (Map<String, ?> movie) {
            switch (layout) {
                case FragmentRecyclerView.LAYOUT_LINEAR:
                    vIcon.setImageResource((Integer) movie.get("image"));
                    vTitle.setText((String) movie.get("name"));
                    vDescription.setText((String) movie.get("description"));
                    ratingBar.setRating((int) Double.parseDouble(movie.get("rating").toString()) + 1);
                    break;

                case FragmentRecyclerView.LAYOUT_GRID:
                    vIcon.setImageResource((Integer) movie.get("image"));
                    break;

                case FragmentRecyclerView.LAYOUT_STAGGERED_GRID:
                    vIcon.setImageResource((Integer) movie.get("image"));
                    vTitle.setText((String) movie.get("name"));
                    vDescription.setText((String) movie.get("description"));
            }
        }
    }

    /**
     * OnMovieMenuClickListener interface for clicks on movie menu.
     * This design pattern allows the listener actions to be defined
     * outside the adapter.
     */
    public interface OnMovieMenuClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnMovieMenuClickListener(final OnMovieMenuClickListener mItemClickListener) {
        this.onMovieMenuClickListener = mItemClickListener;
    }

    /**
     * OnItemClickListener interface for clicks on list items.
     * This design pattern allows the listener actions to be defined
     * outside the adapter.
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = null;

        switch (layout) {
            case FragmentRecyclerView.LAYOUT_LINEAR:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movie_row_linear, parent, false);
                break;

            case FragmentRecyclerView.LAYOUT_GRID:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movie_row_grid, parent, false);
                break;

            case FragmentRecyclerView.LAYOUT_STAGGERED_GRID:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movie_row_stagg_grid, parent, false);
                break;
        }

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_fade_in);
        animation.setDuration(1000);
        v.startAnimation(animation);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        Map<String, ?> movie = mDataSet.get(position);
        holder.bindMovieData(movie);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}