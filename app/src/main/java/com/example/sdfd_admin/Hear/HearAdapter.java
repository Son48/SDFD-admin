package com.example.sdfd_admin.Hear;

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

import com.airbnb.lottie.animation.content.Content;
import com.bumptech.glide.Glide;
import com.example.sdfd_admin.Diabetes.DiabeteUpdate;
import com.example.sdfd_admin.R;

import java.util.List;
import java.util.zip.Inflater;


public class HearAdapter extends RecyclerView.Adapter<HearAdapter.ViewHolder>{
    private Context context;
    private List<HearModel>hearModels;

    public HearAdapter(Context context, List<HearModel> hearModels) {
        this.context = context;
        this.hearModels = hearModels;
    }

    @NonNull
    @Override
    public HearAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hear_dish,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HearAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(hearModels.get(position).getImg_url()).into(holder.diaImg);
        holder.name.setText(hearModels.get(position).getName());
        holder.description.setText(hearModels.get(position).getDescription());
        holder.calo.setText(String.valueOf(hearModels.get(position).getCalo()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, HearUpdate.class);
                intent.putExtra("detail3",hearModels.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hearModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView diaImg;
        TextView name,description,calo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            diaImg=itemView.findViewById(R.id.hear_img);
            name=itemView.findViewById(R.id.name_hitem);
            description=itemView.findViewById(R.id.des_hitem);
            calo=itemView.findViewById(R.id.calo_hitem);
        }
    }
}
