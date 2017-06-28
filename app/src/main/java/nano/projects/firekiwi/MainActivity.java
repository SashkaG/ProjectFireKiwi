package nano.projects.firekiwi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicMarkableReference;

import static nano.projects.firekiwi.R.id.confirm;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    String mName;
    FirebaseDatabase fdb;
    boolean skip = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        final MaterialEditText name = (MaterialEditText)findViewById(R.id.name);
        final MaterialEditText fullname = (MaterialEditText)findViewById(R.id.fullname);
        String a="";
        try {
           a = mAuth.getCurrentUser().getDisplayName();
            Log.v("1234",a);
           skip = !a.equals("");
        }
        catch (NullPointerException e)
        {
            skip = false;
        }
        finally {
            if(skip){
                Intent intent = new Intent(MainActivity.this, SettActivity.class);
                startActivity(intent);
            }
        }
        fdb = FirebaseDatabase.getInstance();
        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals(""))
                {
                    mName = name.getText().toString()+" "+fullname.getText().toString();
                    mAuth.addAuthStateListener(mAuthListener);
                }
            }
        });
    }
    FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user!=null){
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(mName).build();
                user.updateProfile(profileUpdates);
                fdb.getReference("users").child(mAuth.getCurrentUser().getPhoneNumber()).child("name").setValue(mName);
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(mAuth.getCurrentUser().getPhoneNumber());
                fdb.getReference("users").child(mAuth.getCurrentUser().getPhoneNumber()).child("chats").setValue(arrayList);
                Intent intent = new Intent(MainActivity.this, SettActivity.class);
                startActivity(intent);
            }
        }
    };
}
