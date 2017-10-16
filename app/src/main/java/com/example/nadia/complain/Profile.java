package com.example.nadia.complain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    ListView listViewComplains;
    DatabaseReference databaseCompList;
    List<ComplainBox> complainBoxList;
    private Button logout;
    private Button details,saved_complain;
    private FirebaseAuth firebaseAuth;

    // Bundle getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth==null)
        {
            finish();
            Intent i = new Intent(this,Login.class);
            startActivity(i);
        }

        complainBoxList = new ArrayList<>();
        databaseCompList = FirebaseDatabase.getInstance().getReference("Complain");

        logout = (Button) findViewById(R.id.loguotbt);
        details = (Button) findViewById(R.id.details_p);
        saved_complain = (Button)findViewById(R.id.saved_complain);

        listViewComplains = (ListView) findViewById(R.id.listViewComplains);

        logout.setOnClickListener(this);
        details.setOnClickListener(this);
        saved_complain.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseCompList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // we can use this method to read the values of firebase database
                complainBoxList.clear();
                for (DataSnapshot complainSnapshot : dataSnapshot.getChildren()) {
                    ComplainBox co = complainSnapshot.getValue(ComplainBox.class);

                    complainBoxList.add(co);
                }
                ComplainList adapter = new ComplainList(Profile.this, complainBoxList);
                listViewComplains.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ///if error then this fuction will be executed
                // Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();

            }
        });
        listViewComplains.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ComplainBox c = complainBoxList.get((int) id);
                Intent i = new Intent(Profile.this, Complain_details.class);
                i.putExtra("complain_id", c.getIdC());
                Profile.this.startActivity(i);
                //Toast.makeText(getApplicationContext(),"Item Clicked: pos:"+position+ " id: "+c.getIdC(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(Profile.this, Login.class));
        }
        if (view == details) {
            startActivity(new Intent(Profile.this, Profile_details.class));
        }
        if(view==saved_complain)
        {
            startActivity(new Intent(Profile.this,Saved_complain.class));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home :

                startActivity(new Intent(Profile.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(Profile.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(Profile.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(Profile.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(Profile.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


