package com.example.sdfd_admin.Hear;

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

public class HearActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HearAdapter hearAdapter;
    private List<HearModel> hearModels;
    private ProgressBar progressBar;
    private FloatingActionButton addh;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hear);
        progressBar = findViewById(R.id.progressbar5);
        recyclerView = findViewById(R.id.Hear_rec);
        addh=findViewById(R.id.Hadd);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        hearModels = new ArrayList<>();
        hearAdapter = new HearAdapter(this, hearModels);

        recyclerView.setAdapter(hearAdapter);
        db = FirebaseFirestore.getInstance();

        addh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HearActivity.this, HearAdd.class);
                startActivity(intent);
            }
        });

        db.collection("ViewAllHeart").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                HearModel p = d.toObject(HearModel.class);
                                p.setHearid(d.getId());
                                hearModels.add(p);

                            }

                            hearAdapter.notifyDataSetChanged();

                        }


                    }
                });

    }
}