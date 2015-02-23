package com.almasapp.hw6.almasapp6;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFrontPage extends Fragment {

    private OnButtonClickedListener mListener;

    public interface OnButtonClickedListener {
        public void onButtonClicked(int id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnButtonClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public FragmentFrontPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_front_page, container, false);

        Button aboutMe = (Button) rootView.findViewById(R.id.buttonFrontAboutMe);
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(R.id.buttonFrontAboutMe);
            }
        });

        Button viewPager = (Button) rootView.findViewById(R.id.buttonFrontViewPager);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(R.id.buttonFrontViewPager);
            }
        });

        Button recyclerView = (Button) rootView.findViewById(R.id.buttonFrontRecyclerView);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(R.id.buttonFrontRecyclerView);
            }
        });

        return rootView;
    }


}
