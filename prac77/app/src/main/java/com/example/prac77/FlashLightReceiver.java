package com.example.prac77;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class FlashLightReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.example.prac77.TOGGLE_FLASHLIGHT")) {
            boolean status = intent.getBooleanExtra("status", false);
            ((MainActivity) context).switchFlashLight(status);
        }
    }
}

