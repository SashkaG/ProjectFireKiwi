package nano.projects.firekiwi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        numbers.setAdapter(new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,contacts));
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = numbers.getSelectedItem().toString();
                fdb.getReference("users").child(mAuth.getCurrentUser().getPhoneNumber()).child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()!=null)
                        {
                            chats = dataSnapshot.getValue(ArrayList.class);
                            chats.add(number);
                            fdb.getReference("users").child(mAuth.getCurrentUser().getPhoneNumber()).child("chats").setValue(chats);
                            fdb.getReference("users").child(number).child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue()!=null)
                                    {
                                        chats = dataSnapshot.getValue(ArrayList.class);
                                        chats.add(mAuth.getCurrentUser().getPhoneNumber());
                                        fdb.getReference("users").child(number).child("chats").setValue(chats);
                                        Intent intent = new Intent(WriteActivity.this,DialogActivity.class);
                                        startActivity(intent);
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
