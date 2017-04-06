package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;

/**
 * Created by panda_000 on 4/5/2017.
 */

public class LoginActivity extends ServerActivity implements OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new LoginFragment();
        fm.beginTransaction().add(R.id.activity_login, fragment).commit();
    }

    public void login(View v){
        EditText user = (EditText)v.findViewById(R.id.login_USERNAME_FIELD);
        EditText pass = (EditText)v.findViewById(R.id.login_PASSWORD_FIELD);
        String username = user.getText().toString();
        String passhash = pass.getText().toString().hashCode()+"";

        Intent intent = ServerService.serverIntent(this,"login/"+username+":"+passhash);
        startService(intent);
    }

    public void register(View v){
        EditText user = (EditText)v.findViewById(R.id.register_USERNAME_FIELD);
        EditText pass = (EditText)v.findViewById(R.id.register_PASSWORD_FIELD);
        String username = user.getText().toString();
        String passhash = pass.getText().toString().hashCode()+"";

        Intent intent = ServerService.serverIntent(this,"login/"+username+":"+passhash);
        startService(intent);
    }

    public void fragmentClick(View v, int i){
        switch(i){
            case 0:
                Fragment loginFragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_login, loginFragment).commit();
                break;
            case 1:
                register(v);
                break;
            case 2:
                Fragment registerFragment = new RegisterFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_login, registerFragment).commit();
                break;
            case 3:
                login(v);
                break;
        }
    }
}

interface OnFragmentInteractionListener {
    void fragmentClick(View v, int i);
}
