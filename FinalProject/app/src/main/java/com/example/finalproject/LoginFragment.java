package com.example.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginFragment extends android.support.v4.app.Fragment {

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button switchButton = (Button)view.findViewById(R.id.login_SWITCH);
        Button loginButton = (Button)view.findViewById(R.id.login_SUBMIT);
        switchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                mListener.loginFragmentClick(0);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                mListener.loginFragmentClick(1);
            }
        });
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+ " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    interface OnFragmentInteractionListener{
        void loginFragmentClick(int i);
    }
}
