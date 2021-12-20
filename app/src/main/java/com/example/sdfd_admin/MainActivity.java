package com.example.sdfd_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sdfd_admin.Popular.PopularActivity;

public class MainActivity extends AppCompatActivity {
    Button btnp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnp=findViewById(R.id.btnp);
        btnp.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this, PopularActivity.class);
        startActivity(intent);
       }
   });
    }
}