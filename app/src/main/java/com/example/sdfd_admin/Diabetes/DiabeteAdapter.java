package com.example.sdfd_admin.Diabetes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sdfd_admin.Popular.PopularModel;
import com.example.sdfd_admin.Popular.PopularUpdate;
import com.example.sdfd_admin.R;

import java.util.List;

public class DiabeteAdapter extends RecyclerView.Adapter<DiabeteAdapter.ViewHolder> {
    private Context context;
    private List<DiabeteModel> diabeteModels;

    public DiabeteAdapter(Context context, List<DiabeteModel> diabeteModels) {
        this.context = context;
        this.diabeteModels = diabeteModels;
    }

    @NonNull
    @Override
    public DiabeteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.diabites_dish,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(diabeteModels.get(position).getImg_url()).into(holder.diaImg);
        holder.name.setText(diabeteModels.get(position).getName());
        holder.description.setText(diabeteModels.get(position).getDescription());
        holder.calo.setText(String.valueOf(diabeteModels.get(position).getCalo()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DiabeteUpdate.class);
                intent.putExtra("detail2",diabeteModels.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return diabeteModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView diaImg;
        TextView name,description,calo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diaImg=itemView.findViewById(R.id.diabetes_img);
            name=itemView.findViewById(R.id.name_diaitem);
            description=itemView.findViewById(R.id.des_diaitem);
            calo=itemView.findViewById(R.id.calo_diaitem);
        }
    }
}
