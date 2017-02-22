package com.lenovo.ahqmrf.firechat.activities;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.lenovo.ahqmrf.firechat.R;
import com.lenovo.ahqmrf.firechat.adapter.MessageAdapter;
import com.lenovo.ahqmrf.firechat.model.Message;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mChatList;
    private MessageAdapter mAdapter;
    private Button btnSend;
    private EditText msgText;
    private String mId;
    private ArrayList<Message> msgList;
    private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatList = (RecyclerView) findViewById(R.id.rv_msg_list);
        btnSend = (Button) findViewById(R.id.btn_send);
        msgText = (EditText) findViewById(R.id.et_msg);
        msgList = new ArrayList<>();
        mId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        mChatList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MessageAdapter(this, mId, msgList);
        mChatList.setAdapter(mAdapter);

        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase("https://chat-application-804ef.firebaseio.com/").child("chat");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msgText.getText().toString();

                if (!message.isEmpty()) {
                    /**
                     * Firebase - Send message
                     */
                    mFirebaseRef.push().setValue(new Message(mId, message));
                }

                msgText.setText("");
            }
        });

        mFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    try{

                        Message model = dataSnapshot.getValue(Message.class);

                        msgList.add(model);
                        mChatList.scrollToPosition(msgList.size() - 1);
                        mAdapter.notifyItemInserted(msgList.size() - 1);
                    } catch (Exception ex) {
                        Log.e("Chat activity", ex.getMessage());
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
    }

}
