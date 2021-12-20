package com.example.sdfd_admin.Hear;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sdfd_admin.R;
import com.example.sdfd_admin.weightloss.WeightLossActivity;
import com.example.sdfd_admin.weightloss.WeightLossModel;
import com.example.sdfd_admin.weightloss.WeightLossUpdate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HearUpdate extends AppCompatActivity implements View.OnClickListener{
    FirebaseFirestore dbroot;
    EditText name,des,calo,type,time;
    WeightLossModel weightLossModel;
    RoundedImageView img_dia;
    private EditText updatepic;
    StorageReference storageReference;
    private FirebaseFirestore db;
    final int IMAGE_REQUEST=77;
    Uri imageLocation;
    private int textAlignment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hear_update);
        name = findViewById(R.id.name_hup);
        des = findViewById(R.id.description_hup);
        calo = findViewById(R.id.calo_hup);
        img_dia=findViewById(R.id.h_imgup);
        type=findViewById(R.id.type_hup);
        time=findViewById(R.id.time_hup);
        dbroot = FirebaseFirestore.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference("heart-related diseaes");
        weightLossModel = (WeightLossModel) getIntent().getSerializableExtra("detail4");

        Glide.with(getApplicationContext()).load(weightLossModel.getImg_url()).into(img_dia);
        calo.setText(String.valueOf(weightLossModel.getCalo()));
        name.setText(weightLossModel.getName());
        des.setText(weightLossModel.getDescription());
        type.setText(weightLossModel.getType());
        time.setText(weightLossModel.getTime());

        findViewById(R.id.update_hup).setOnClickListener(this);
        findViewById(R.id.delete_hdl).setOnClickListener(this);
        findViewById(R.id.addh_imageup).setOnClickListener(this);
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
                        dbroot.collection("ViewAllHeart").document(weightLossModel.getWlid())
                                .update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(HearUpdate.this, "Dish Updated", Toast.LENGTH_LONG).show();
                                    }
                                });

                    }
                });
            }
        });



    }

    private void deleteProduct() {
        dbroot.collection("ViewAllHeart").document(weightLossModel.getWlid()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(HearUpdate.this, "Dish deleted", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(HearUpdate.this, WeightLossActivity.class));
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

                img_dia.setImageBitmap(bitmap);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_hup:
                updateProduct(imageLocation);
                break;
            case R.id.delete_hdl:

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
            case R.id.addh_imageup:
                openFileChooser();
                break;
        }
    }

}