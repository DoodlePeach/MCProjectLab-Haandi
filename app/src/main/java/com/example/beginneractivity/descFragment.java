package com.example.beginneractivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;

public class descFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private SearchItem currentItem;
    private LinkedList<SearchItem> allOfTheItems;

    private Suggetions_List_Adapter adapter;
    Handler handler = new Handler();
    ListView pref;

    int saad_suggest_varaible;

    ArrayList<String> dishName = new ArrayList<String>();
    ArrayList<String> dishResturants = new ArrayList<String>();
    ArrayList<String> dishPrices = new ArrayList<String>();
    ArrayList<String> dishDescription = new ArrayList<String>();
    ArrayList<String> images = new ArrayList<String>();


    public descFragment() {
        // Required empty public constructor
    }

    public static descFragment newInstance(SearchItem item, LinkedList<SearchItem> allOfTheItems) {
        descFragment fragment = new descFragment();

        Bundle args = new Bundle();
        args.putSerializable("selectedItem", item);
        args.putSerializable("fullList", allOfTheItems);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        currentItem = (SearchItem) arguments.getSerializable("selectedItem");
        allOfTheItems = (LinkedList<SearchItem>) arguments.getSerializable("fullList");
        Take_Adapter_And_Set_Preference_list();


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

        Picasso.get().load(Uri.parse(currentItem.getImageSrc())).fit().into(imageView);
        dishDesc.setText(currentItem.dishDescription);
        dishPrice.setText(currentItem.dishPrice + "$");
        dishResturant.setText(currentItem.dishResturant);

        Drawable stars = ratingBar.getProgressDrawable();
        stars.setTint(Color.WHITE);

        pref = v.findViewById(R.id.list_pref);

        adapter = new Suggetions_List_Adapter(getContext(), images, dishName, dishResturants, dishDescription, dishPrices, handler);
        pref.setAdapter(adapter);

        pref.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.onFragmentInteraction(allOfTheItems.get(i), allOfTheItems);
            }
        });


        return v;
    }

    public void onButtonPressed(String tag, Uri uri) {
        if (mListener != null) {
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
        void onFragmentInteraction(SearchItem item, LinkedList<SearchItem> items);
    }



    public void Take_Adapter_And_Set_Preference_list() {
        for (int i = 0; i < allOfTheItems.size(); i++) {
            dishName.add(allOfTheItems.get(i).dishName);
            dishResturants.add(allOfTheItems.get(i).dishResturant);
            dishPrices.add(allOfTheItems.get(i).dishPrice.toString());
            dishDescription.add(allOfTheItems.get(i).dishDescription);
            images.add(allOfTheItems.get(i).imageSrc);
        }

    }
}