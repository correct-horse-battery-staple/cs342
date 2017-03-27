package com.example.finalproject;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class ServerService extends IntentService {

    public ServerService() {
        super("ServerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
        }
    }

}
