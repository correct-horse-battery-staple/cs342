package com.example.myamherstkevin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;

/**
 * Created by panda_000 on 2/8/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    final String loginError = "Invalid username/password; retype or register now";
    final String invalidError = "Username/password cannot contain spaces";
    static ArrayList<ArrayList<String>> usernamePasswordInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void login(View view){
        Toast.makeText(this,"login",Toast.LENGTH_SHORT).show();
        TextView errorMessage = (TextView)findViewById(R.id.register_error_text);

        String user = getUserI();
        String pass = getPassI();
        Log.d("login",user+" "+pass);
        if(user.contains(" ")||pass.contains(" ")||user.isEmpty()||pass.isEmpty()){
            errorMessage.setText(invalidError);
        }
        else {
            boolean valid = false;
            try {
                File file = getFileStreamPath("userpass.txt");
                file.createNewFile();
                InputStream input = this.openFileInput(file.getName());
                InputStreamReader inputreader = new InputStreamReader(input);
                BufferedReader buffreader = new BufferedReader(inputreader);

                if (input != null) {
                    String line;
                    while ((line = buffreader.readLine()) != null) {
                        String[] parts = line.split("\\s");
                        if(parts.length!=0) {
                            if (parts[0].equals(user) && parts[1].equals(pass)) {
                                valid = true;
                                break;
                            }
                        }
                    }
                }
                input.close();
            } catch (FileNotFoundException f) {
                Log.e("Error", "FileNotFoundError " + f.toString());
            } catch (IOException e) {
                Log.e("Error", "IOError " + e.toString());
            }

            if (valid) {
                Toast.makeText(this, "Successful login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                errorMessage.setText(loginError);
            }
        }
    }

    public void register(View view){
        TextView errorMessage = (TextView)findViewById(R.id.register_error_text);

        String user = getUserI();
        String pass = getPassI();
        if(user.contains(" ")||pass.contains(" ")){
            errorMessage.setText(invalidError);
        }
        else {
            try {
                File file = getFileStreamPath("userpass.txt");
                if(!file.exists()){
                    file.createNewFile();
                }
                OutputStreamWriter output = new OutputStreamWriter(this.openFileOutput(file.getName(), Context.MODE_APPEND));
                Log.d("outputstream",this.openFileOutput("userpass.txt",Context.MODE_APPEND).toString());
                output.append("\n"+user+" "+pass);
                //Toast.makeText(this,user+" "+pass,Toast.LENGTH_SHORT).show();
                output.flush();
                output.close();
            }
            catch (IOException i){
                i.printStackTrace();
            }
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_register);
        if(fragment == null){
            fragment = new RegisterFragment();
            fm.beginTransaction().add(R.id.activity_register, fragment).commit();
        }
    }

    private String getUserI(){
        EditText username = (EditText)findViewById(R.id.username);
        String user = username.getText().toString();
        return user;
    }

    private String getPassI(){
        EditText password = (EditText)findViewById(R.id.password);
        String pass = password.getText().toString();
        return pass;
    }

}
