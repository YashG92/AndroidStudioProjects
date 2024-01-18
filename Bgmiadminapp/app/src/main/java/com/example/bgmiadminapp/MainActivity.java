package com.example.bgmiadminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView createMatchCard,UploadCard,deleteCard;
  private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMatchCard=findViewById(R.id.createMatchCard);
        createMatchCard.setOnClickListener(this);
        UploadCard=findViewById(R.id.UploadCard);
        UploadCard.setOnClickListener(this);
        deleteCard=findViewById(R.id.deleteCard);
        deleteCard.setOnClickListener(this);



    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.createMatc
        }
    }

    }
