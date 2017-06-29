package nano.projects.firekiwi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DialogActivity extends AppCompatActivity {

    String chat;
    String name;
    String name2;
    String user;
    String user2;
    FirebaseAuth mAuth;
    FirebaseDatabase fdb;
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
        chat = intent.getStringExtra("chat");
    }

}
