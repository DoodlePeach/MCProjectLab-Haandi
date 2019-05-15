package com.example.beginneractivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;


public class promosFragment extends Fragment {
    private User loggedInUser = null;
    private OnUserDemandPromos mListener;

    public promosFragment() {
        // Required empty public constructor
    }

    public static promosFragment newInstance(User loggedInUser) {
        promosFragment fragment = new promosFragment();
        Bundle args = new Bundle();
        args.putSerializable("loggedInUser", loggedInUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle arguments = getArguments();

            loggedInUser = (User) arguments.get("loggedInUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflated = inflater.inflate(R.layout.fragment_promos, container, false);

        if(loggedInUser != null)
        {
            mListener.onDemandedListFetched(loggedInUser);
        }

        else
            inflated.findViewById(R.id.tv_no_user).setVisibility(View.VISIBLE);

        return inflated;
    }

    public void onButtonPressed(User user) {
        if (mListener != null) {
            mListener.onDemandedListFetched(user);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserDemandPromos) {
            mListener = (OnUserDemandPromos) context;
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


    public interface OnUserDemandPromos
    {
        public LinkedList <Promos> onDemandedListFetched(User user);
    }
}
