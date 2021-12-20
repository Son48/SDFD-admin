package com.example.sdfd_admin.weightloss;

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
import com.example.sdfd_admin.Diabetes.DiabeteModel;
import com.example.sdfd_admin.Diabetes.DiabeteUpdate;
import com.example.sdfd_admin.R;

import java.util.List;


public class WeightLossAdapter extends RecyclerView.Adapter<WeightLossAdapter.ViewHolder>{
    private Context context;
    private List<WeightLossModel> weightLossModels;

    public WeightLossAdapter(Context context, List<WeightLossModel> weightLossModels) {
        this.context = context;
        this.weightLossModels = weightLossModels;
    }

    @NonNull
    @Override
    public WeightLossAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.weightloss_dish,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeightLossAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(weightLossModels.get(position).getImg_url()).into(holder.wlImg);
        holder.name.setText(weightLossModels.get(position).getName());
        holder.description.setText(weightLossModels.get(position).getDescription());
        holder.calo.setText(String.valueOf(weightLossModels.get(position).getCalo()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, WeightLossUpdate.class);
                intent.putExtra("detail3",weightLossModels.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weightLossModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView wlImg;
        TextView name,description,calo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wlImg=itemView.findViewById(R.id.weightloss_img);
            name=itemView.findViewById(R.id.name_wlitem);
            description=itemView.findViewById(R.id.des_wlitem);
            calo=itemView.findViewById(R.id.calo_wlitem);
        }
    }
}
