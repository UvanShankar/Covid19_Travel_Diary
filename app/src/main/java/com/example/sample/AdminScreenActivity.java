package com.example.sample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminScreenActivity extends AppCompatActivity {
ListView listView;
ArrayList<String> arrayList,userid;
ArrayAdapter arrayAdapter;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);
        final ProgressDialog po = new ProgressDialog(this);
        po.setMessage("Loading" );
        po.show();
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        listView=findViewById(R.id.listview2);
        arrayList=new ArrayList<>();
        userid=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        firebaseDatabase.getReference().child("my_users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String usern=dataSnapshot.child("username").getValue().toString();
                String userd=dataSnapshot.getKey();

userid.add(userd);
                arrayList.add(usern);
                arrayAdapter.notifyDataSetChanged();
                po.dismiss();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
            for(String us:arrayList)
            {
                if(us.equals(dataSnapshot.child("username").getValue().toString())) {
                    arrayList.remove(i);
                    userid.remove(i);
                }i++;
            }
            arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
Intent in=new Intent(AdminScreenActivity.this,ViewUserActivity.class);
in.putExtra("userid",userid.get(position));
startActivity(in);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.logoutadmin)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent in=new Intent(AdminScreenActivity.this,AdminLoginActivity.class);
            startActivity(in);
        }
        return super.onOptionsItemSelected(item);
    }
}
