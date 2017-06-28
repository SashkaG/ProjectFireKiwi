package nano.projects.firekiwi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase fdb;
    ArrayList chats;
    ArrayList contacts;
    ListView chatss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        chatss = (ListView)findViewById(R.id.chatlist);
        fdb.getReference("users").child(mAuth.getCurrentUser().getPhoneNumber()).child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    chats = dataSnapshot.getValue(ArrayList.class);
                    chatss.setAdapter(new ChatAdapter(Main2Activity.this,chats));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contacts = new ArrayList<String>();
                final WriteActivity act = new WriteActivity();
                Intent intent = new Intent(Main2Activity.this,act.getClass());
                startActivity(intent);
            }
        });
    }

}
