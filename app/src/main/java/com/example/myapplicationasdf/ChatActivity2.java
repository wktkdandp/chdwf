package com.example.myapplicationasdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class ChatActivity2 extends AppCompatActivity {
    private static final String TAG = "ChatActivity2";
    private RecyclerView RecyclerView;
    CustomAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText etText;
    Button btnSend;
    String stEmail;
    FirebaseDatabase database;
    ArrayList<Chat> chatArrayList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        database = FirebaseDatabase.getInstance();
        stEmail=getIntent().getStringExtra("email");
        chatArrayList=new ArrayList<>();
        Button done= (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { finish(); }
        });
        btnSend = (Button)findViewById(R.id.btnSend);

        etText=(EditText)findViewById(R.id.etText);
        RecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        RecyclerView.setLayoutManager(layoutManager);
        String[] myDataset={"t1","t2","t3","t4"};
        mAdapter=new CustomAdapter(chatArrayList,stEmail);
        RecyclerView.setAdapter(mAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Chat chat = dataSnapshot.getValue(Chat.class);
                String commentKey = dataSnapshot.getKey();
                String stEmail = chat.getEmail();
                String stText = chat.getText();
                Log.d(TAG, "stEmail: "+stEmail);
                Log.d(TAG, "stText: "+stText);
                chatArrayList.add(chat);
                mAdapter.notifyDataSetChanged();
                 //...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Chat chat = dataSnapshot.getValue(Chat.class);

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.


                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.


                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(ChatActivity2.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        DatabaseReference databaseReference = database.getReference("메세지");
        databaseReference.addChildEventListener(childEventListener);

        btnSend.setOnClickListener((v)->{
                String stText=etText.getText().toString();
                //Toast.makeText(ChatActivity2.this,"메세지:"+stText,Toast.LENGTH_LONG).show();


                Calendar c= Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String datetime = dateformat.format(c.getTime());
                DatabaseReference myRef = database.getReference("메세지").child(datetime);
                Hashtable<String,String> numbers
                    =new Hashtable<String, String>();
                numbers.put("email",stEmail);
                numbers.put("text",stText);


                myRef.setValue(numbers);

        });
    }
}