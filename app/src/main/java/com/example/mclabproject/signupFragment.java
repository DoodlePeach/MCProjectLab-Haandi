package com.example.mclabproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signupFragment extends Fragment {
    private OnSignupButtonClicked mListener;

    public signupFragment() {
        // Required empty public constructor
    }

    public static signupFragment newInstance() {
        signupFragment fragment = new signupFragment();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View inflated = inflater.inflate(R.layout.signup_layout_file, container, false);

        Button signupButton = inflated.findViewById(R.id.btn_signup);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String umail = ((EditText) inflated.findViewById(R.id.et_email_signup)).getText().toString();
                final String upass = ((EditText) inflated.findViewById(R.id.et_password_signup)).getText().toString();
                final String uname = ((EditText) inflated.findViewById(R.id.et_email_name)).getText().toString();

                final User info = new User(uname, umail, upass);

                OnTaskDone taskDone = new OnTaskDone() {
                    @Override
                    public void takeRelevantActions(boolean found) {
                        if(found)
                            Toast.makeText(getContext(), "That username/password is invalid. Please try another combination.", Toast.LENGTH_SHORT).show();

                        else
                        {
                            mListener.makeUser(info);
                            Toast.makeText(getContext(), "Success!.", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                    }

                    @Override
                    public void connectionError() {
                        Toast.makeText(getContext(), "There was an connection error.", Toast.LENGTH_SHORT).show();
                    }
                };

                if(!(uname.isEmpty() || umail.isEmpty() || upass.isEmpty()))
                    onButtonPressed(info, taskDone);

                else
                    Toast.makeText(getContext(), "Please enter data in all of the fields.", Toast.LENGTH_SHORT).show();
            }
        });

        return inflated;
    }

    public void onButtonPressed(User info, OnTaskDone taskDone) {
        if (mListener != null) {
            mListener.onSignupButtonClicked(info, taskDone);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignupButtonClicked) {
            mListener = (OnSignupButtonClicked) context;
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

    public interface OnSignupButtonClicked {
        void onSignupButtonClicked(User info, OnTaskDone taskDone);
        void makeUser(User info);
    }

}
