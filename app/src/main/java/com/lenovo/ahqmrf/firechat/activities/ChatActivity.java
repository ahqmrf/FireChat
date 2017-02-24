package com.lenovo.ahqmrf.firechat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.lenovo.ahqmrf.firechat.R;
import com.lenovo.ahqmrf.firechat.adapter.MessageAdapter;
import com.lenovo.ahqmrf.firechat.model.Message;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mChatList;
    private MessageAdapter mAdapter;
    private Button btnSend;
    private EditText msgText;
    private String mId;
    private ArrayList<Message> msgList;
    private Firebase mFirebaseRef;
    private FirebaseAuth mFirebaseAuth;
    private String sentTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Firebase.setAndroidContext(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() != null) mId = mFirebaseAuth.getCurrentUser().getEmail();
        else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        sentTo = getIntent().getStringExtra("sent_to");


        setTitle(sentTo);
        mChatList = (RecyclerView) findViewById(R.id.rv_msg_list);
        btnSend = (Button) findViewById(R.id.btn_send);
        msgText = (EditText) findViewById(R.id.et_msg);
        msgList = new ArrayList<>();
        mChatList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MessageAdapter(this, mId, msgList);
        mChatList.setAdapter(mAdapter);

        getOverflowMenu();

        mFirebaseRef = new Firebase("https://chat-application-804ef.firebaseio.com/").child("chat");


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msgText.getText().toString();

                if (!message.isEmpty()) {
                    /**
                     * Firebase - Send message
                     */
                    mFirebaseRef.push().setValue(new Message(mId, message, sentTo));
                }

                msgText.setText("");
            }
        });

        mFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    try {
                        Message model = dataSnapshot.getValue(Message.class);
                        if (model.getSentTo().equals(sentTo) && model.getId().equals(mId)) {
                            msgList.add(model);
                            mChatList.scrollToPosition(msgList.size() - 1);
                            mAdapter.notifyItemInserted(msgList.size() - 1);
                        } else if (model.getSentTo().equals(mId) && model.getId().equals(sentTo)) {
                            msgList.add(model);
                            mChatList.scrollToPosition(msgList.size() - 1);
                            mAdapter.notifyItemInserted(msgList.size() - 1);
                        }
                    } catch (Exception ex) {
                        //Log.e("Chat activity", ex.getMessage());
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        msgText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_logout:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
