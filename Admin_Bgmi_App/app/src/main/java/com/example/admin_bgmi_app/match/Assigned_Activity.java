package com.example.admin_bgmi_app.match;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin_bgmi_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Assigned_Activity extends AppCompatActivity {

    private RecyclerView assignedRecycler;
    private ProgressBar progressBar;
    private ArrayList<Reference_Data>list;
    private Reference_Adapter reference_adapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned);

        assignedRecycler = findViewById(R.id.assignedRecycler);
        progressBar = findViewById(R.id.progressBar);
        reference = FirebaseDatabase.getInstance().getReference().child("Reference number");

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        assignedRecycler.setLayoutManager(gridLayoutManager);
        assignedRecycler.setHasFixedSize(true);

        getData();

    }

    private void getData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    Reference_Data data = snapshot1.getValue(Reference_Data.class);
                    list.add(0,data);
                }
                reference_adapter = new Reference_Adapter(Assigned_Activity.this,list);
                reference_adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                assignedRecycler.setAdapter(reference_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Assigned_Activity.this, "No data found"+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}