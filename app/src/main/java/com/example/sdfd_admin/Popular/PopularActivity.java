package com.example.sdfd_admin.Popular;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sdfd_admin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PopularActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PopularAdapter popularAdapter;
    private List<PopularModel> popularModelList;
    private ProgressBar progressBar;
    private FloatingActionButton addpo;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.popular_rec);
        addpo=findViewById(R.id.fadd);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        popularModelList = new ArrayList<>();
        popularAdapter = new PopularAdapter(this, popularModelList);

        recyclerView.setAdapter(popularAdapter);
        db = FirebaseFirestore.getInstance();

        addpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PopularActivity.this, PopularAdd.class);
                startActivity(intent);
            }
        });

        db.collection("ViewAllItem").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressBar.setVisibility(View.GONE);

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                PopularModel p = d.toObject(PopularModel.class);
                                p.setId(d.getId());
                                popularModelList.add(p);

                            }

                            popularAdapter.notifyDataSetChanged();

                        }


                    }
                });

    }
}