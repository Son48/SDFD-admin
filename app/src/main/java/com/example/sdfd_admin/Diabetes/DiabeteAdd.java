package com.example.sdfd_admin.Diabetes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sdfd_admin.Popular.PopularActivity;
import com.example.sdfd_admin.Popular.PopularAdd;
import com.example.sdfd_admin.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

public class DiabeteAdd extends AppCompatActivity implements View.OnClickListener {

    RoundedImageView roundedImageViewDia;
    private EditText editTextName;
    private EditText editTextCalo;
    private EditText editTextDesc;
    private EditText editTextType;
    private EditText editTextTime;
    private EditText uploadpic;

    StorageReference storageReference;
    private FirebaseFirestore db;
    final int IMAGE_REQUEST = 77;
    Uri imageLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabete_add);


        editTextName = findViewById(R.id.edittext_name1);
        editTextCalo = findViewById(R.id.edittext_calo1);
        editTextDesc = findViewById(R.id.edittext_desc1);
        editTextTime = findViewById(R.id.edittext_time1);
        editTextType = findViewById(R.id.edittext_type1);
        uploadpic = findViewById(R.id.edittext_uploadpictext1);
        roundedImageViewDia = findViewById(R.id.diabetep_img);


        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Diabetes");

        findViewById(R.id.textview_view_products1).setOnClickListener(this);
        findViewById(R.id.add_diabetep_image).setOnClickListener(this);
    }

    private String getExtension(Uri uri) {
        try {
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

            return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private void saveProduct() {

        try {
            if (!uploadpic.getText().toString().isEmpty() && imageLocation != null) {
                String nameOfImage = uploadpic.getText().toString() + "." + getExtension(imageLocation);
                StorageReference ref = storageReference.child(nameOfImage);

                UploadTask uploadTask = ref.putFile(imageLocation);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Map<String, String> map = new HashMap<>();
                            map.put("name", editTextName.getText().toString());
                            map.put("calo", editTextCalo.getText().toString());
                            map.put("description", editTextDesc.getText().toString());
                            map.put("time", editTextTime.getText().toString());
                            map.put("type", editTextType.getText().toString());
                            map.put("img_url", task.getResult().toString());

                            db.collection("ViewAllDiabetes").add(map)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(DiabeteAdd.this, "Dish Added", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DiabeteAdd.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                        }
                    }
                });
            } else {

            }

        } catch (Exception e) {

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null &&data.getData()!=null){
                imageLocation =data.getData();
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageLocation);

                roundedImageViewDia.setImageBitmap(bitmap);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_save1:
                saveProduct();
                break;
            case R.id.textview_view_products1:
                startActivity(new Intent(this, PopularActivity.class));
                break;
            case  R.id.add_diabetep_image:
                openFileChooser();
                break;

        }
    }
}