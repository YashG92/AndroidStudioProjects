package com.example.admin_bgmi_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admin_bgmi_app.delete.DeleteMatch_Fragment;
import com.example.admin_bgmi_app.match.Assigned_Activity;
import com.example.admin_bgmi_app.match.CreateMatch_Fragment;
import com.example.admin_bgmi_app.upload.Upload_Fragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView createMatchCard, UploadCard, DeleteCard,assignedCard;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMatchCard = findViewById(R.id.createMatchCard);
        createMatchCard.setOnClickListener(this);
        UploadCard = findViewById(R.id.UploadCard);
        UploadCard.setOnClickListener(this);
        DeleteCard = findViewById(R.id.DeleteCard);
        DeleteCard.setOnClickListener(this);
        assignedCard=findViewById(R.id.assignedCard);
        assignedCard.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.createMatchCard:
                CreateMatch_Fragment createMatch_fragment = new CreateMatch_Fragment();

                fragmentManager.beginTransaction().replace(R.id.frame_layout,createMatch_fragment).addToBackStack("home").commit();
                break;

            case R.id.UploadCard:
                Upload_Fragment upload_fragment = new Upload_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,upload_fragment).addToBackStack("home").commit();
                break;

            case R.id.DeleteCard:
                DeleteMatch_Fragment deleteMatch_fragment = new DeleteMatch_Fragment();
                fragmentManager.beginTransaction().replace(R.id.frame_layout,deleteMatch_fragment).addToBackStack("home").commit();
                break;
            case R.id.assignedCard:
                startActivity(new Intent(this, Assigned_Activity.class));
                break;

        }
    }
}