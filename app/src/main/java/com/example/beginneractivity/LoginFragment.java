package com.example.beginneractivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment{
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        final View inflated = inflater.inflate(R.layout.login_layout, container, false);

        Button loginButton = inflated.findViewById(R.id.button_login_login);
        Button signupButton = inflated.findViewById(R.id.bt_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String umail = ((EditText) inflated.findViewById(R.id.editText_email)).getText().toString();
                final String upass = ((EditText) inflated.findViewById(R.id.editText_login)).getText().toString();

                if(!(umail.isEmpty() || upass.isEmpty()))
                {
                    User inputInfo = new User(umail, upass);
                    OnTaskDone taskDone = new OnTaskDone() {
                        @Override
                        public void takeRelevantActions(boolean found) {
                            if(found)
                                Toast.makeText(getContext(), "Successfully logged in!", Toast.LENGTH_SHORT).show(); // This is placeholder. TODO

                            else
                                Toast.makeText(getContext(), "Invalid username/password.", Toast.LENGTH_SHORT).show(); // This is placeholder. TODO
                        }

                        @Override
                        public void connectionError()
                        {
                            Toast.makeText(getContext(), "There was an connection error.", Toast.LENGTH_SHORT).show();
                        }
                    };

                    sendUserInfoToMain(inputInfo, taskDone);
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentChange("[LAUNCH] signupFragment", null);
            }
        });

        return inflated;
    }

    public void sendUserInfoToMain(User info, OnTaskDone listner) {
        if (mListener != null) {
            mListener.onLoginButtonPressed(info, listner);
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
        void onLoginButtonPressed(User info, OnTaskDone listner);
        void onFragmentChange(String tag, Uri uri);
    }
}
