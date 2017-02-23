package com.lenovo.ahqmrf.firechat.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.lenovo.ahqmrf.firechat.R;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private EditText etUserEmail;
    private EditText etPassword;
    private ProgressDialog dialog;
    private FirebaseAuth boss;
    private View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(getSupportActionBar() != null) getSupportActionBar().hide();

        etUserEmail = (EditText) findViewById(R.id.et_email_or_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        registerBtn = (Button) findViewById(R.id.btn_register);
        dialog = new ProgressDialog(this);
        boss = FirebaseAuth.getInstance();
        layout = getWindow().getDecorView().findViewById(R.id.activity_register);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryRegisteringUser();
            }
        });
    }

    private void tryRegisteringUser() {
        String email = etUserEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Snackbar.make(layout, "Enter a valid email address", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password) || password.length() < 6) {
            Snackbar.make(layout, "Password must be at least 6 characters long", Snackbar.LENGTH_SHORT).show();
            return;
        }

        dialog.setMessage("Registering user, please wait...");
        dialog.show();

        boss.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if(task.isSuccessful()) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    Snackbar.make(layout, "Unexpected error, please try again.", Snackbar.LENGTH_SHORT).show();
                    etUserEmail.setText("");
                    etPassword.setText("");
                }
            }
        });
    }
}
