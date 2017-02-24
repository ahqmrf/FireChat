package com.lenovo.ahqmrf.firechat.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.lenovo.ahqmrf.firechat.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerBtn;
    private Button loginBtn;
    private EditText etUserEmail;
    private EditText etPassword;
    private ProgressDialog dialog;
    private FirebaseAuth boss;
    private View layout;
    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null) getSupportActionBar().hide();

        etUserEmail = (EditText) findViewById(R.id.et_email_or_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        registerBtn = (Button) findViewById(R.id.btn_register);
        loginBtn = (Button) findViewById(R.id.btn_login);
        layout = getWindow().getDecorView().findViewById(R.id.activity_login);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        boss = FirebaseAuth.getInstance();

        backPressed = false;

    }

    private void userLogin() {
        String email = etUserEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        dialog.setMessage("Logging in...");
        dialog.show();

        boss.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if(task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, ChatListActivity.class));
                    finish();
                }
                else {
                    Snackbar.make(layout, "Invalid email or password", Snackbar.LENGTH_SHORT).show();
                    etUserEmail.setText("");
                    etPassword.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
        else if(v.getId() == R.id.btn_login) {
            userLogin();
        }
    }

    @Override
    public void onBackPressed() {
        if(backPressed) {
            backPressed = false;
            finishAffinity();
        }
        else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            backPressed = true;
        }
    }
}
