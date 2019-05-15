package com.example.beginneractivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class searchFragment extends Fragment{
    private OnFragmentInteractionListener mListener;
    private String locationSelection, categorySelection;
    private View rootView;
    private Spinner catSpinner = null, locSpinner = null;


    public searchFragment() {
        // Required empty public constructor
    }

    public static searchFragment newInstance() {
        searchFragment fragment = new searchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = rootView = container;

        v = inflater.inflate(R.layout.fragment_begin_search, container, false);

        catSpinner = v.findViewById(R.id.spinner_perferred_cusine);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, MainActivity.categories);
        catSpinner.setAdapter(catAdapter);

        locSpinner = v.findViewById(R.id.spinner_enter_city);
        ArrayAdapter<String> locAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, MainActivity.locations);
        locSpinner.setAdapter(locAdapter);

        Button button = v.findViewById(R.id.button_find_me_food);

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelection = MainActivity.categories.get(position);

                if(parent.getChildAt(0) != null)
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        locSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationSelection = MainActivity.locations.get(position);
                locSpinner.setSelection(position);

                if(parent.getChildAt(0) != null)
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(categorySelection == null || locationSelection == null))
                {
                    LinkedList<SearchItem> filtered = new LinkedList<>();

                    for(SearchItem item : MainActivity.nonPromoItems)
                        if(item.getDishCat().equals(categorySelection) && item.getResturantLocation().equals(locationSelection))
                            filtered.add(item);

                    onButtonPressed(filtered);
                }

                else
                    Toast.makeText(getActivity().getApplicationContext(), "Incomplete Selection.", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    public void onButtonPressed(LinkedList<SearchItem> itemsToPass) {
        if (mListener != null) {
            mListener.onFragmentSearchButtonClick(itemsToPass);
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
        void onFragmentSearchButtonClick(LinkedList<SearchItem> itemsToPass);
    }

    public void setOnFragmentInteractionListner(OnFragmentInteractionListener listener)
    {
        this.mListener = listener;
    }

    public void refresh()
    {
        Spinner catSpinner = rootView.findViewById(R.id.spinner_perferred_cusine);
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, MainActivity.categories);
        catSpinner.setAdapter(catAdapter);

        Spinner locSpinner = rootView.findViewById(R.id.spinner_enter_city);
        ArrayAdapter<String> locAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, MainActivity.locations);
        locSpinner.setAdapter(locAdapter);
    }
}
