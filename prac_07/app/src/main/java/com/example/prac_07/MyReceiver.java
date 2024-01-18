package com.example.prac_07;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.widget.Toast;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public void onReceive(Context context,Intent intent){
        Toast.makeText(context,"Intent Dectected.",Toast.LENGTH_LONG).show();
    }
}
