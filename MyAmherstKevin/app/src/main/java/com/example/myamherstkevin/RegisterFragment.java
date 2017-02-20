package com.example.myamherstkevin;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by panda_000 on 2/9/2017.
 */

public class RegisterFragment extends Fragment {
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
        View v= inflater.inflate(R.layout.fragment_register, container, false);

        verifyPermissions(getActivity());

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivityForResult(takePictureIntent, 1);
//            File photo = null;
//            try {
//                photo = File.createTempFile("IMAGE",".jpg");
//            }
//            catch(IOException i){
//                i.printStackTrace();
//            }
//            if(photo!=null) {
//                Uri photoUri = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider", photo);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                startActivityForResult(takePictureIntent, 1);
//            }
//        }


        return v;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static void verifyPermissions(Activity activity) {
        // Check if we have read or write permission
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        String[] permissions = {Manifest.permission.CAMERA};
        if (cameraPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,permissions,1);
        }
    }
}
