package nano.projects.firekiwi;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettActivity extends AppCompatActivity {

    String TAG = "123";
    String cardNumb;
    String userName;
    String userPhone;
    EditText card;
    Button add;
    FirebaseAuth mAuth;
    FirebaseDatabase fdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett);
        card = (EditText)findViewById(R.id.cardNumb);
        mAuth=FirebaseAuth.getInstance();
        fdb=FirebaseDatabase.getInstance();
        add = (Button)findViewById(R.id.add);
        userName=mAuth.getCurrentUser().getDisplayName();
        userPhone=mAuth.getCurrentUser().getPhoneNumber();
        fdb.getReference("users").child(userPhone).child("card").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try
                {
                    cardNumb = dataSnapshot.getValue().toString();
                }
                catch (NullPointerException e)
                {
                    cardNumb=null;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(cardNumb==null || cardNumb=="")
        {
            add.setVisibility(View.GONE);//card already exist
            card.setEnabled(false);
        }
        else {
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardNumb = card.getText().toString();
                    //TODO: register kiwi card
                    fdb.getReference("users").child(userPhone).child("card").setValue(cardNumb);
                }
            });
        }
    }
}
