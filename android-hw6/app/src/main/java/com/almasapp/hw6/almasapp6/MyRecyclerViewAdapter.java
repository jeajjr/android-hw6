package com.almasapp.hw6.almasapp6;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by José Ernesto on 12/02/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Map<String, ?>> mDataSet;
    private Context context;
    OnItemClickListener onItemClickListener;
    //OnCheckBoxClickListener onCheckBoxClickListener;

    public MyRecyclerViewAdapter(Context context, List<Map<String, ?>> mDataSet) {
        this.mDataSet = mDataSet;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public CheckBox vCheckBox;
        public RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.imageViewMoviesImage);
            vTitle = (TextView) v.findViewById(R.id.textViewMoviesTitle);
            vDescription = (TextView) v.findViewById(R.id.textViewMoviesDescription);
            //vCheckBox = (CheckBox) v.findViewById(R.id.checkBoxMovies);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBarMovie);

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
/*
            vCheckBox.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (onCheckBoxClickListener != null) {
                        onCheckBoxClickListener.onCheckBoxClick(v, getPosition());
                    }
                }
            });
            */
        }

        public void bindMovieData (Map<String, ?> movie) {
            vTitle.setText((String) movie.get("name"));
            vDescription.setText((String) movie.get("description"));
            vIcon.setImageResource((Integer) movie.get("image"));
            //vCheckBox.setChecked((Boolean) movie.get("selected"));
            ratingBar.setRating((int) Double.parseDouble(movie.get("rating").toString()) + 1);
        }
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

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    /**
     * OnCheckBoxClickListener interface for clicks on the item's CheckKBox.
     */
    /*
    public interface OnCheckBoxClickListener {
        public void onCheckBoxClick(View view, int position);
    }
    public void setOnCheckBoxClickListener(final OnCheckBoxClickListener onCheckBoxClickListener) {
        this.onCheckBoxClickListener = onCheckBoxClickListener;
    }
*/
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false);

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