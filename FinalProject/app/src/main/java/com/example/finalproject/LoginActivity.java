package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by panda_000 on 4/5/2017.
 */

public class LoginActivity extends ServerActivity implements LoginFragment.OnFragmentInteractionListener,RegisterFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new LoginFragment();
        fm.beginTransaction().add(R.id.activity_login, fragment).commit();
    }

    public void login(){
        EditText user = (EditText)findViewById(R.id.login_USERNAME_FIELD);
        EditText pass = (EditText)findViewById(R.id.login_PASSWORD_FIELD);
        String userhash = user.getText().toString().hashCode()+"";
        String passhash = pass.getText().toString().hashCode()+"";

        if(userhash.length()>0&&passhash.length()>0){
            Intent intent = ServerService.serverIntent(this,"login/"+userhash+":"+passhash);
            startService(intent);
        }
        else{
            setErrorMessage("Enter a valid username/password");
        }

    }

    public void register(){
        EditText user = (EditText)findViewById(R.id.register_USERNAME_FIELD);
        EditText pass = (EditText)findViewById(R.id.register_PASSWORD_FIELD);
        String userhash = user.getText().toString().hashCode()+"";
        String passhash = pass.getText().toString().hashCode()+"";

        if(userhash.length()>0&&passhash.length()>0){
            Intent intent = ServerService.serverIntent(this,"register/"+userhash+":"+passhash);
            startService(intent);
        }
        else{
            setErrorMessage("Enter a valid username/password");
        }
    }

    public void loginFragmentClick(int i){
        switch(i){
            case 0:
                Fragment registerFragment = new RegisterFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_login, registerFragment).commit();
                break;
            case 1:
                login();
                break;
        }
    }

    public void registerFragmentClick(int i){
        switch(i){
            case 0:
                Fragment loginFragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_login, loginFragment).commit();
                break;
            case 1:
                register();
                break;
        }
    }

    public void setErrorMessage(String s){
        TextView textView = (TextView)findViewById(R.id.login_ERROR);
        textView.setText(s);
    }
}
