package com.example.prac77;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CameraManager mCameraManager;
    private String mCameraId;
    private ToggleButton toggleButton;

    private BroadcastReceiver flashlightReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean status = intent.getBooleanExtra("flashlight_status", false);
            switchFlashLight(status);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if(!isFlashAvailable){
            showNoFlashError();
        }
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        toggleButton = findViewById(R.id.onOffFlashlight);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchFlashLight(b);

                // Send broadcast to toggle flashlight
                Intent intent = new Intent("com.example.prac77.TOGGLE_FLASHLIGHT");
                intent.putExtra("status", b);
                sendBroadcast(intent);
            }
        });


        // Register the broadcast receiver
        IntentFilter filter = new IntentFilter("com.example.prac77.TOGGLE_FLASHLIGHT");
        registerReceiver(flashlightReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the broadcast receiver
        unregisterReceiver(flashlightReceiver);
    }

    public void showNoFlashError(){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Oops");
        alert.setMessage("Flash not available");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.show();
    }

    public void switchFlashLight(boolean status){
        try {
            mCameraManager.setTorchMode(mCameraId, status);
        } catch (CameraAccessException e){
            e.printStackTrace();
            Toast.makeText(this, "Error accessing the flashlight.", Toast.LENGTH_SHORT).show();
        }
    }
}
