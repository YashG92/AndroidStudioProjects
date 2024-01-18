package com.example.bgmiadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView createMatchCard, UploadCard, DeleteCard;
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

        }
    }
}