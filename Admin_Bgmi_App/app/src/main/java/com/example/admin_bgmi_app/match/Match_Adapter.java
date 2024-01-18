package com.example.admin_bgmi_app.match;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_bgmi_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Match_Adapter extends RecyclerView.Adapter<Match_Adapter.matchViewAdapter> {

    private Context context;
    private List<Match_Data> list;

    public Match_Adapter(Context context, List<Match_Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public matchViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.match_layout,parent,false);
        return new matchViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull matchViewAdapter holder, int position) {
        Match_Data currentItem = list.get(position);
        holder.matchTime.setText("Match Time: "+currentItem.getMatchTime());
        holder.matchDate.setText("Match Date: "+currentItem.getMatchDate());
        holder.uploadTime.setText("Upload Time: "+currentItem.getUploadTime());
        holder.uploadDate.setText("Upload Date: "+currentItem.getMatchDate());
        holder.referenceID.setText("Reference Id: "+currentItem.getReferId());
        holder.RoomId.setText("Room Id: "+currentItem.getRoom_Id());
        holder.RoomPass.setText("Room Pass: "+currentItem.getRoom_pass());
        holder.price.setText("Price: "+currentItem.getMatchCharge());
        holder.matchDuration.setText("Match Duration: "+currentItem.getMatchDuration());
        holder.matchCategory.setText("Match Category: "+currentItem.getMatchCategory());
        holder.reward.setText("Rewards: "+currentItem.getReward());

        Picasso.get().load(currentItem.getImageUrl()).into(holder.ticketImage);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this ticket?");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int currentPosition = holder.getAdapterPosition();
                        if (currentPosition != RecyclerView.NO_POSITION) {
                            Match_Data currentItem = list.get(currentPosition);

                            DatabaseReference reference;
                            reference = FirebaseDatabase.getInstance().getReference().child("Matches");
                            reference.child(currentItem.getMatchDuration()).child(currentItem.getReferId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(view.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });

                            list.remove(currentPosition);
                            notifyItemRemoved(currentPosition);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); // Dismiss the dialog when Cancel is clicked
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


//        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage(" Are you sure you want  delete this ticket?");
//                builder.setCancelable(true);
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        DatabaseReference reference;
//                        reference = FirebaseDatabase.getInstance().getReference().child("Matches");
//                        reference.child(currentItem.getMatchDuration()).child(currentItem.getReferId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(view.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        notifyItemRemoved(position);
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        AlertDialog dialog = null;
//                        try {
//                            dialog = builder.create();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        if (dialog!=null){
//                            dialog.show();
//                        }
//                    }
//                });
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class matchViewAdapter extends RecyclerView.ViewHolder {

        private ImageView ticketImage;
        private TextView uploadDate,uploadTime,referenceID,RoomId,RoomPass,price,matchDuration,matchCategory,reward,matchDate,matchTime;
        private Button deleteBtn;
        public matchViewAdapter(@NonNull View itemView) {
            super(itemView);

            ticketImage = itemView.findViewById(R.id.ticketImage);
            uploadDate = itemView.findViewById(R.id.uploadDate);
            uploadTime = itemView.findViewById(R.id.uploadTime);
            referenceID = itemView.findViewById(R.id.referenceID);
            RoomId = itemView.findViewById(R.id.roomId);
            RoomPass = itemView.findViewById(R.id.roomPassword);
            price = itemView.findViewById(R.id.price);
            matchDuration = itemView.findViewById(R.id.matchDuration);
            matchCategory = itemView.findViewById(R.id.matchCategory);
            reward = itemView.findViewById(R.id.matchRewards);
            matchDate = itemView.findViewById(R.id.matchDate);
            matchTime = itemView.findViewById(R.id.matchTime);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);



        }
    }
}
