package com.example.admin_bgmi_app.match;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_bgmi_app.R;

import java.util.List;

public class Reference_Adapter extends RecyclerView.Adapter<Reference_Adapter.referenceViewAdapter> {

    public Reference_Adapter(Context context, List<Reference_Data> list) {
        this.context = context;
        this.list = list;
    }

    private Context context;
    private List<Reference_Data> list;
    @NonNull
    @Override
    public referenceViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reference_layout,parent,false);
        return new referenceViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull referenceViewAdapter holder, int position) {

        Reference_Data currentItem = list.get(position);
        holder.textView.setText(currentItem.getReferID());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class referenceViewAdapter extends RecyclerView.ViewHolder {

        private TextView textView;

        public referenceViewAdapter(@NonNull View itemView) {

            super(itemView);
            textView=itemView.findViewById(R.id.textView);
        }

    }
}
