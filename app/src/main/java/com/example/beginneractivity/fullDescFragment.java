package com.example.beginneractivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fullDescFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fullDescFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fullDescFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private SearchItem currentItem;

    public fullDescFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static fullDescFragment newInstance(SearchItem item) {
        fullDescFragment fragment = new fullDescFragment();

        Bundle args = new Bundle();
        args.putSerializable("args", item);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        currentItem = (SearchItem) arguments.getSerializable("args");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = container;

        v = inflater.inflate(R.layout.fragment_full_desc, container, false);

        ImageView imageView = v.findViewById(R.id.dish_img);
        TextView dishDesc = v.findViewById(R.id.dish_name);
        TextView dishPrice = v.findViewById(R.id.dish_price);
        TextView dishResturant = v.findViewById(R.id.dish_resturant);
        RatingBar ratingBar = v.findViewById(R.id.ratingBar);

        imageView.setImageResource(currentItem.imageSrc);
        dishDesc.setText(currentItem.dishDescription);
        dishPrice.setText(currentItem.dishPrice + "$");
        dishResturant.setText(currentItem.dishResturant);
        ratingBar.setNumStars(4);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String tag, Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(tag, uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String tag, Uri uri);
    }
}