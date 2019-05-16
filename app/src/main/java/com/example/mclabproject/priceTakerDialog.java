package com.example.mclabproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class priceTakerDialog extends DialogFragment {
    private OnCorrectValuesEntered mListener;

    public priceTakerDialog() {
        // Required empty public constructor
    }

    public static priceTakerDialog newInstance() {
        priceTakerDialog fragment = new priceTakerDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View inflated = inflater.inflate(R.layout.fragment_price_taker_dialog, container, false);

        Button submitButton = inflated.findViewById(R.id.btn_submit_range);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startRange = ((EditText) inflated.findViewById(R.id.et_start_range)).getText().toString();
                String endRange = ((EditText) inflated.findViewById(R.id.et_end_range)).getText().toString();

                if(!(startRange.isEmpty() || endRange.isEmpty()))
                {
                    Integer startRangeInt = Integer.parseInt(startRange);
                    Integer endRangeInt = Integer.parseInt(endRange);

                    if(startRangeInt > endRangeInt)
                        Toast.makeText(getContext(), "Error, start range is greater than end range.", Toast.LENGTH_SHORT).show();

                    else
                    {
                        searchListFragment fragment = (searchListFragment) getTargetFragment();
                        Intent intent = new Intent();
                        intent.putExtra("startRange", startRangeInt);
                        intent.putExtra("endRange", endRangeInt);

                        fragment.onActivityResult(1, 1, intent);
                        dismiss();
                    }
                }

                else
                    Toast.makeText(getContext(), "Please fill both of the ranges.", Toast.LENGTH_SHORT).show();
            }
        });

        return inflated;
    }

    public void onButtonPressed(Integer startRange, Integer endRange) {
        if (mListener != null) {
            mListener.setRangesGiven(startRange, endRange);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCorrectValuesEntered) {
            mListener = (OnCorrectValuesEntered) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCorrectValuesEntered");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCorrectValuesEntered {
        void setRangesGiven(Integer startRange, Integer endRange);
    }
}
