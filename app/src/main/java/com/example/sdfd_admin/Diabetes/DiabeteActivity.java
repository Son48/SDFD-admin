package com.example.sdfd_admin.Diabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sdfd_admin.Popular.PopularActivity;
import com.example.sdfd_admin.Popular.PopularAdapter;
import com.example.sdfd_admin.Popular.PopularAdd;
import com.example.sdfd_admin.Popular.PopularModel;
import com.example.sdfd_admin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiabeteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DiabeteAdapter diabeteAdapter;
    private List<DiabeteModel> diabeteModels;
    private ProgressBar progressBar;
    private FloatingActionButton adddia;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabete);
        progressBar = findViewById(R.id.progressbar1);
        recyclerView = findViewById(R.id.diabete_rec);
        adddia=findViewById(R.id.add_diabetep);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        diabeteModels = new ArrayList<>();
        diabeteAdapter = new DiabeteAdapter(this, diabeteModels);

        recyclerView.setAdapter(diabeteAdapter);
        db = FirebaseFirestore.getInstance();

        adddia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiabeteActivity.this, DiabeteAdd.class);
                startActivity(intent);
            }
        });

        db.collection("ViewAllDiabetes").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                DiabeteModel diabeteModel = d.toObject(DiabeteModel.class);
                                diabeteModel.setDiaid(d.getId());
                                diabeteModels.add(diabeteModel);

                            }

                            diabeteAdapter.notifyDataSetChanged();

                        }


                    }
                });

    }
}