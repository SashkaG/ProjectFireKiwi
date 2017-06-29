package nano.projects.firekiwi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DialogActivity extends AppCompatActivity {

    String chat;
    String name;
    String name2;
    boolean fromto;
    String user;
    String user2;
    FirebaseAuth mAuth;
    ListView messages;
    FirebaseDatabase fdb;
    private FirebaseListAdapter<ChatMessage> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        name = mAuth.getCurrentUser().getDisplayName();
        user = mAuth.getCurrentUser().getPhoneNumber();
        Intent intent = getIntent();
        name2 = intent.getStringExtra("name2");
        user2 = intent.getStringExtra("user2");
        chat = intent.getStringExtra("chat");// p_id == chat
        fromto = chat.split("_")[0].equals(name);
        Log.w("1234",chat.split("_")[0]);
        messages = (ListView)findViewById(R.id.messages);
        Button sendmess = (Button)findViewById(R.id.sendmess);
        final EditText text = (EditText)findViewById(R.id.editText);
        sendmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> a = new HashMap<String, String>();
                a.put("text",text.getText().toString());
                sendMessage(chat,fromto,a,"simple");
            }
        });
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.message, fdb.getReference("chats").child(chat).child("messages")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                if(model.type=="simple")
                {
                    TextView messageText = (TextView)v.findViewById(R.id.text);
                    TextView messageTime = (TextView)v.findViewById(R.id.time);

                    // Set their text
                    messageText.setText(model.data.get("text"));
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.time));
                }
                // Get references to the views of message.xml
            }
        };
        messages.setAdapter(adapter);
    }
    public void sendMessage(final String p_id, final boolean p_fromTo, final Map<String,String> p_data, final String p_type){
        fdb.getReference("chats").child(chat).child("m_id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long m_id = dataSnapshot.getValue(long.class);
                ChatMessage nm = new ChatMessage(p_id.concat("_"+m_id),p_fromTo,p_data,p_type);
                fdb.getReference("chats").child(chat).child("m_id").setValue(m_id+1);
                fdb.getReference("chats").child(chat).child("messages").push().setValue(nm);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
