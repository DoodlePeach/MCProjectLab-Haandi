package com.example.mclabproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflated = inflater.inflate(R.layout.fragment_promos, container, false);

        if(loggedInUser != null)
        {
            TextView loggedInUsername = inflated.findViewById(R.id.tv_username);
            loggedInUsername.setText(loggedInUser.getUname());

            OnDataFetched fetchedListener = new OnDataFetched() {
                @Override
                public void callbackDataFetched(LinkedList<Promos> fetchedItems) {
                    ListView promosList = inflated.findViewById(R.id.lv_promos);
                    PromosAdapter adapter = new PromosAdapter(getContext(), R.layout.promos_layout, fetchedItems);
                    promosList.setAdapter(adapter);
                }
            };

            mListener.onDemandedListFetched(loggedInUser, fetchedListener);
        }

        else
        {
             inflated.findViewById(R.id.tv_no_user).setVisibility(View.VISIBLE);
             inflated.findViewById(R.id.tv_text_logged_in).setVisibility(View.INVISIBLE);
        }

        return inflated;
    }

    public void onButtonPressed(User user, OnDataFetched dataFetched) {
        if (mListener != null) {
            mListener.onDemandedListFetched(user, dataFetched);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserDemandPromos) {
            mListener = (OnUserDemandPromos) context;
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


    public interface OnUserDemandPromos
    {
      void onDemandedListFetched(User user, OnDataFetched dataFetched);
    }

    public interface OnDataFetched
    {
        void callbackDataFetched(LinkedList<Promos> fetchedItems);

    }
}
