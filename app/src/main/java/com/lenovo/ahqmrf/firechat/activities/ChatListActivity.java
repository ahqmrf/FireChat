package com.lenovo.ahqmrf.firechat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lenovo.ahqmrf.firechat.R;

public class ChatListActivity extends AppCompatActivity {

    private EditText etTargetUser;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        etTargetUser = (EditText) findViewById(R.id.et_user_email);
        startBtn = (Button) findViewById(R.id.btn_start_chatting);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("sent_to", etTargetUser.getText().toString());
                startActivity(intent);
            }
        });
    }
}
