package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    String country[]={"USA","UK"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this,R.layout.list_item,country);
        listView.setAdapter(arrayAdapter);


        Spinner spinner =findViewById(R.id.myspin);


    }
}