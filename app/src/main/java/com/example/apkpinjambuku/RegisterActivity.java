package com.example.apkpinjambuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText editName, editEmail, editPassword, editPasswordConfirm;
    private Button btnRegister, btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editName = findViewById(R.id.name);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        editPasswordConfirm = findViewById(R.id.password_confirmation);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        //progressDialog.setCancelMessage("mohon di tunggu");
        progressDialog.setCancelable(false);


        btnLogin.setOnClickListener(v -> {
            finish();
        });
        btnRegister.setOnClickListener(v -> {
            if(editName.getText().length()>0 && editEmail.getText().length()>0 && editPassword.getText().length()>0 && editPasswordConfirm.getText().length()>0) {
                if (editPassword.getText().toString().equals(editPasswordConfirm.getText().toString())){
                   register(editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "silahkan masukan password yang sama!", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "silahkan isi data anda!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void register(String name, String email, String password){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult>task) {
            if (task.isSuccessful()){
            }else{
                Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
            }

        });
    }


    private void reload() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}