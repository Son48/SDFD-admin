package com.example.sdfd_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button btnsg,btnlogin2,btnreset;
    ProgressBar pb;
    TextInputLayout txtEmail,txtPassword;

    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        btnlogin2=findViewById(R.id.btn_login2);
        txtEmail=findViewById(R.id.emailUser);
        txtPassword=findViewById(R.id.passwordUser);
        btnreset=findViewById(R.id.reset_pass);
        pb=findViewById(R.id.prg_bar2);

        fauth =FirebaseAuth.getInstance();

        //reset pass
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetEmail=new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog =new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link ");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email= resetEmail.getText().toString();
                        fauth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginActivity.this, "Reset Link Sent To Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error ! Reset Link Is Not Sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                passwordResetDialog.create().show();

            }
        });

        //login
        btnlogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Admin").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String emailuser=txtEmail.getEditText().getText().toString().trim();
                        String pass=txtPassword.getEditText().getText().toString().trim();
                        if(emailuser.isEmpty()){
                            txtEmail.setError("You have not entered your email!");
                            txtEmail.requestFocus();
                            return;
                        }

                        if(pass.isEmpty()){
                            txtPassword.setError("You have not entered the password!");
                            txtPassword.requestFocus();
                            return;
                        }

                        if(!Patterns.EMAIL_ADDRESS.matcher(emailuser).matches()){
                            txtEmail.setError("Invalid email!");
                            txtEmail.requestFocus();
                            return;
                        }


                        pb.setVisibility(View.VISIBLE);
                        fauth.signInWithEmailAndPassword(emailuser,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent it = new Intent(LoginActivity.this, MainActivity2.class);
                                    startActivity(it);
                                    pb.setVisibility(View.GONE);
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                                    pb.setVisibility(View.GONE);
                                }
                            }
                        });
                    }

                });

            }

        });

    }
}