package com.lenovo.ahqmrf.firechat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lenovo.ahqmrf.firechat.R;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button loginBtn;
    private Button registerBtn;
    private EditText userEmail;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = (EditText) findViewById(R.id.et_email_or_username);
        password = (EditText) findViewById(R.id.et_password);
        loginBtn = (Button) findViewById(R.id.btn_login);
        registerBtn = (Button) findViewById(R.id.btn_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginUser();
                break;
            case R.id.btn_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    private void loginUser() {
        String inputUserName = userEmail.getText().toString();
        String inputPassword = password.getText().toString();

    }
}
