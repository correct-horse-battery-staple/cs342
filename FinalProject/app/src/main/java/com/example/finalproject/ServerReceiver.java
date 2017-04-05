package com.example.finalproject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by panda_000 on 3/22/2017.
 */

public class ServerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ((ServerActivity)context).receiveServer(intent);
    }
}
