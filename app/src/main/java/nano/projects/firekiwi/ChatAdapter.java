package nano.projects.firekiwi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nano.projects.firekiwi.R;

/**
 * Created by Никита on 28.06.2017.
 */

public class ChatAdapter extends BaseAdapter{

    FirebaseDatabase fdb;
    Context ctx;
    String namee;
    LayoutInflater lInflater;
    ArrayList<String> objects;

    ChatAdapter(Context context, ArrayList<String> chats) {
        ctx = context;
        objects = chats;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fdb=FirebaseDatabase.getInstance();
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.chat, parent, false);
        }
        final TextView name = (TextView)view.findViewById(R.id.chatname);
        fdb.getReference("users").child(getItem(position).toString()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                namee=dataSnapshot.getValue(String.class);
                name.setText(namee);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}
