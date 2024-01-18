package com.example.bgmiadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateMatch_Fragment extends Fragment {
    
    private EditText match_Date, match_Time,reference_ID, slots,match_Charge,rewards;
    private Spinner spinner1, spinner2;
    private Button upload_ImageBtn,upload;
    private ImageView imageView;
    private final int REQ = 1;
    private Bitmap bitmap;
    private String category1, category2;
    private ProgressDialog pd;
    private DatabaseReference reference;
    private StorageReference storageReference;

    public CreateMatch_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_match_, container, false);

        match_Date = view.findViewById(R.id.match_Date);
        match_Time = view.findViewById(R.id.match_Time);
        reference_ID = view.findViewById(R.id.reference_ID);
        slots = view.findViewById(R.id.slots);
        match_Charge = view.findViewById(R.id.match_charge);
        rewards = view.findViewById(R.id.rewards);
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        upload_ImageBtn = view.findViewById(R.id.upload_ImgBtn);
        upload = view.findViewById(R.id.upload);
        imageView = view.findViewById(R.id.imageView);
        reference= FirebaseDatabase.getInstance().getReference().child("Matches");
        storageReference = FirebaseStorage.getInstance().getReference().child("Matches");
        pd = new ProgressDialog(getContext());


        String[] items = new String[]{"Select match duration","Morning","Afternoon","Evening"};
        spinner1.setAdapter(new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,items));
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category1 = spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] items2 = new String[]{"Select match Category","Erangle","Livik","TDM"};
        spinner2.setAdapter(new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,items2));

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category2 = spinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        
        upload_ImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform Firebase anonymous sign-in and upload operations here
                FirebaseAuth.getInstance().signInAnonymously()
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User signed in anonymously successfully
                                    uploadImage(); // Upload image to Firebase Storage
                                } else {
                                    // Handle sign-in failure
                                    Toast.makeText(getContext(), "Anonymous sign-in failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



        return view;
    }

    private void checkValidation() {
        uploadImage();
    }

    private void uploadImage() {

        pd.setMessage("Uploading...");
        pd.setCancelable(false);
        pd.show();
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,boas);
        byte[] finalImage=boas.toByteArray();
        final StorageReference filePath;
        filePath =  storageReference.child(reference_ID.getText().toString()+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    uploadData(uri);
                                    Toast.makeText(getContext(),"Done",Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),"something went wrong", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void uploadData(Uri uri) {
        String matchDate = match_Date.getText().toString();
        String matchTime = match_Time.getText().toString();
        String referID = reference_ID.getText().toString();
        String slot = slots.getText().toString();
        String matchCharge = match_Charge.getText().toString();
        String roomId = "Not Available";
        String roomPass = "Not Available";
        String reward = rewards.getText().toString();

        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-mm-yy");
        String date = currentDate.format(callForDate.getTime());

        Calendar callForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(callForTime.getTime());

        Match_Data match_data =  new Match_Data(date,time,referID,matchCharge,slot,matchDate,matchTime,uri.toString(),category1,category2,roomId,roomPass,reward);
        reference.child(category1).child(referID).setValue(match_data);

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ && resultCode==getActivity().RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
            }catch (IOException e){
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }
    }
}