package com.example.sdfd_admin.Popular;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.sdfd_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PopularUpdate extends AppCompatActivity implements View.OnClickListener{
    FirebaseFirestore dbroot;
    EditText name,des,calo,type,time;
    PopularModel popularModel;
    ImageView popularimg;
    private EditText updatepic;
    StorageReference storageReference;
    private FirebaseFirestore db;
    final int IMAGE_REQUEST=77;
    Uri imageLocation;
    private int textAlignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_update);
        name = findViewById(R.id.name_popular);
        des = findViewById(R.id.description_popular);
        calo = findViewById(R.id.calo_popular);
        popularimg=findViewById(R.id.popular_imgup);
        type=findViewById(R.id.type_popular);
        time=findViewById(R.id.time_popular);
        dbroot = FirebaseFirestore.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference("Popular");
        popularModel = (PopularModel) getIntent().getSerializableExtra("detail");

        Glide.with(getApplicationContext()).load(popularModel.getImg_url()).into(popularimg);
        calo.setText(String.valueOf(popularModel.getCalo()));
        name.setText(popularModel.getName());
        des.setText(popularModel.getDescription());
        type.setText(popularModel.getType());
        time.setText(popularModel.getTime());

        findViewById(R.id.update_popular).setOnClickListener(this);
        findViewById(R.id.delete_popular).setOnClickListener(this);
        findViewById(R.id.addpopular_imageup).setOnClickListener(this);
    }
    private String getExtension(Uri uri){
        try {
            ContentResolver contentResolver=getContentResolver();
            MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();

            return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    private  void openFileChooser(){
        Intent intent =new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private void updateProduct(Uri file) {

        StorageReference riversRef = storageReference.child(Objects.requireNonNull(file.getLastPathSegment()));



        riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        Map<String,Object> map=new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("calo",calo.getText().toString());
                        map.put("description",des.getText().toString());
                        map.put("type",type.getText().toString());
                        map.put("time",time.getText().toString());
                        map.put("img_url",downloadUrl.toString());
                        dbroot.collection("ViewAllItem").document(popularModel.getId())
                                .update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(PopularUpdate.this, "Product Updated", Toast.LENGTH_LONG).show();
                                    }
                                });

                    }
                });
            }
        });



    }

    private void deleteProduct() {
        dbroot.collection("PopularDish").document(popularModel.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PopularUpdate.this, "Product deleted", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(PopularUpdate.this, PopularActivity.class));
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null &&data.getData()!=null){
                imageLocation =data.getData();
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageLocation);

                popularimg.setImageBitmap(bitmap);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_popular:
                updateProduct(imageLocation);
                break;
            case R.id.delete_popular:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure about this?");
                builder.setMessage("Deletion is permanent...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

                break;
            case R.id.addpopular_imageup:
                openFileChooser();
                break;
        }
    }
}
