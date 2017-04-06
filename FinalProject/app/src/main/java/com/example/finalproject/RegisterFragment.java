package com.example.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RegisterFragment extends android.support.v4.app.Fragment {

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button switchButton = (Button)view.findViewById(R.id.register_SWITCH);
        Button registerButton = (Button)view.findViewById(R.id.register_SUBMIT);
        switchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                mListener.fragmentClick(0);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                mListener.fragmentClick(1);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragment.OnFragmentInteractionListener) {
            mListener = (RegisterFragment.OnFragmentInteractionListener) context;
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
        void fragmentClick(int i);
    }
}
