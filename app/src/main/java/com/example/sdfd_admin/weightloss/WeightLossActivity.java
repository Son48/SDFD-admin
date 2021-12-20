package com.example.sdfd_admin.weightloss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sdfd_admin.Diabetes.DiabeteActivity;
import com.example.sdfd_admin.Diabetes.DiabeteAdapter;
import com.example.sdfd_admin.Diabetes.DiabeteAdd;
import com.example.sdfd_admin.Diabetes.DiabeteModel;
import com.example.sdfd_admin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WeightLossActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WeightLossAdapter weightLossAdapter;
    private List<WeightLossModel> weightLossModels;
    private ProgressBar progressBar;
    private FloatingActionButton adddia;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss);
        progressBar = findViewById(R.id.progressbar2);
        recyclerView = findViewById(R.id.weightloss_rec);
        adddia=findViewById(R.id.add_weightloss_dc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        weightLossModels = new ArrayList<>();
        weightLossAdapter = new WeightLossAdapter(this, weightLossModels);

        recyclerView.setAdapter(weightLossAdapter);
        db = FirebaseFirestore.getInstance();

        adddia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WeightLossActivity.this, WeightLossAdd.class);
                startActivity(intent);
            }
        });

        db.collection("ViewAllWeightLoss").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                WeightLossModel weightLossModel = d.toObject(WeightLossModel.class);
                                weightLossModel.setWlid(d.getId());
                                weightLossModels.add(weightLossModel);

                            }

                            weightLossAdapter.notifyDataSetChanged();

                        }


                    }
                });

    }
}