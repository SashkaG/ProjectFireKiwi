package nano.projects.firekiwi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase fdb;
    ListView chatss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        chatss = (ListView)findViewById(R.id.chatlist);
        chatss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user2 = (String) chatss.getItemAtPosition(position);
                String user = mAuth.getCurrentUser().getPhoneNumber();
                String[] e = new String[]{user,user2};
                Arrays.sort(e);
                String chat =e[0]+"_"+e[1];
                String name2 = ((TextView)view.findViewById(R.id.chatname)).getText().toString();
                Intent intent = new Intent(Main2Activity.this,DialogActivity.class);
                intent.putExtra("chat",chat);
                intent.putExtra("name2",name2);
                intent.putExtra("user2",user2);
                startActivity(intent);
            }
        });
        fdb.getReference("users").child(mAuth.getCurrentUser().getPhoneNumber()).child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    Log.v("1234",dataSnapshot.getValue().toString());
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>(){};
                    ArrayList<String> chats = dataSnapshot.getValue(t);
                    chats.remove(mAuth.getCurrentUser().getDisplayName());
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
                Intent intent = new Intent(Main2Activity.this,WriteActivity.class);
                startActivity(intent);
            }
        });
    }

}
