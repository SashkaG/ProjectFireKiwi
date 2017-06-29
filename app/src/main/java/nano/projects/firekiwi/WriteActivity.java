package nano.projects.firekiwi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class WriteActivity extends AppCompatActivity {
    public ArrayList contacts;
    ArrayList chats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        final Spinner numbers = (Spinner)findViewById(R.id.spinner);
        Button write = (Button)findViewById(R.id.button5);
        fdb.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contacts =new ArrayList();
                for(DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){
                    contacts.add(dataSnapshot2.getKey());
                }
                numbers.setAdapter(new ArrayAdapter(WriteActivity.this,R.layout.support_simple_spinner_dropdown_item,contacts));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = numbers.getSelectedItem().toString();
                fdb.getReference("users").child(mAuth.getCurrentUser().getPhoneNumber()).child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()!=null)
                        {
                            GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>(){};
                            chats = dataSnapshot.getValue(t);
                            if(chats.contains(number))
                            {
                                Intent intent = new Intent(WriteActivity.this,DialogActivity.class);
                                startActivity(intent);
                                return;
                            }
                            chats.add(number);
                            fdb.getReference("users").child(mAuth.getCurrentUser().getPhoneNumber()).child("chats").setValue(chats);
                            fdb.getReference("users").child(number).child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue()!=null)
                                    {
                                        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>(){};
                                        chats = dataSnapshot.getValue(t);
                                        chats.add(mAuth.getCurrentUser().getPhoneNumber());
                                        fdb.getReference("users").child(number).child("chats").setValue(chats);
                                        final String user2 = number;
                                        String user = mAuth.getCurrentUser().getPhoneNumber();
                                        String[] e = new String[]{user,user2};
                                        Arrays.sort(e);
                                        final String chat =e[0]+"_"+e[1];
                                        fdb.getReference("chats").child(chat).child("user1").setValue(user);
                                        fdb.getReference("chats").child(chat).child("user2").setValue(user2);
                                        fdb.getReference("chats").child(chat).child("balance").setValue(0.00);
                                        fdb.getReference("chats").child(chat).child("m_id").setValue(0);
                                        fdb.getReference("users").child(number).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String name2 = dataSnapshot.getValue(String.class);
                                                Intent intent = new Intent(WriteActivity.this,DialogActivity.class);
                                                intent.putExtra("chat",chat);
                                                intent.putExtra("name2",name2);
                                                intent.putExtra("user2",user2);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
