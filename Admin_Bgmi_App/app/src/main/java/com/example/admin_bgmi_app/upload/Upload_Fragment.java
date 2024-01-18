package com.example.admin_bgmi_app.upload;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin_bgmi_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Upload_Fragment extends Fragment {

    private EditText reference_id, room_id, room_pass;
    private Spinner spinner;
    private Button upload;
    private DatabaseReference reference;
    private String category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_, container, false);

        reference_id =  view.findViewById(R.id.reference_id);
        room_id =  view.findViewById(R.id.room_id);
        room_pass =  view.findViewById(R.id.room_pass);
        spinner =  view.findViewById(R.id.spinner);
        upload =  view.findViewById(R.id.upload);
        reference = FirebaseDatabase.getInstance().getReference().child("Matches");

        String[] items = new String[]{"Select match duration","Morning","Afternoon","Evening"};
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item,items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category= spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        return view;
    }

    private void checkValidation() {
        if(reference_id.getText().toString().isEmpty()){
            reference_id.setError("Empty");
            reference_id.requestFocus();
        } else if (room_id.getText().toString().isEmpty()) {
            room_id.setError("Empty");
            room_id.requestFocus();
        } else if (room_pass.getText().toString().isEmpty()) {
            room_pass.setError("Empty");
            room_pass.requestFocus();
        } else if (category.equals("Select match duration")) {
            Toast.makeText(getContext(), "please select match duration", Toast.LENGTH_SHORT).show();
        }else {
            uploadData();
        }

    }

    private void uploadData() {

        HashMap hashMap= new HashMap();
        hashMap.put("room_Id",room_id.getText().toString());
        hashMap.put("room_pass",room_pass.getText().toString());
        reference.child(category).child(reference_id.getText().toString()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Toast.makeText(getContext(), "uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}