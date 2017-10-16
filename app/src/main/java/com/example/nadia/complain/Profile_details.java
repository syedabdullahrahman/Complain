package com.example.nadia.complain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_details extends AppCompatActivity implements View.OnClickListener {
    private Button update, logout;
    private TextView name, email, phone, police_id, rank, post_code, password;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth == null) {
            finish();
            startActivity(new Intent(this, Login.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String uid = user.getUid();
        database = FirebaseDatabase.getInstance().getReference().child("AuthorProfile").child(uid);
        update = (Button) findViewById(R.id.update);
        logout = (Button) findViewById(R.id.logout);

        name = (TextView) findViewById(R.id.name_pd);
        email = (TextView) findViewById(R.id.email_pd);
        phone = (TextView) findViewById(R.id.phone_pd);
        police_id = (TextView) findViewById(R.id.policeId_pd);
        rank = (TextView) findViewById(R.id.rank_pd);
        post_code = (TextView) findViewById(R.id.postCode_pd);
        password = (TextView) findViewById(R.id.password_pd);
        update.setOnClickListener(this);
        logout.setOnClickListener(this);

        ValueEventListener post = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation us = dataSnapshot.getValue(UserInformation.class);
                name.setText(us.getName());
                email.setText(us.getEmail());
                phone.setText(us.getPhone());
                police_id.setText(us.getPoliceId());
                rank.setText(us.getRank());
                post_code.setText(us.getPostcode());
                //password.setText(us.getPassword());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        database.addValueEventListener(post);
    }

    @Override
    public void onClick(View v) {
        if (v == update) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String uid = user.getUid();
            database = FirebaseDatabase.getInstance().getReference().child("AuthorProfile");

            String em = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String nam = name.getText().toString().trim();
            String phn = phone.getText().toString().trim();
            String postCode = post_code.getText().toString().trim();
            String policeId = police_id.getText().toString().trim();
            String rak = rank.getText().toString().trim();

            UserInformation usr = new UserInformation(nam, em, phn, policeId, rak, postCode, uid);

            database.child(uid).setValue(usr);
            user.updatePassword(pass);
            finish();
            startActivity(new Intent(this, Profile_details.class));
        }
        if (v == logout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, Login.class));
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

                startActivity(new Intent(Profile_details.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(Profile_details.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(Profile_details.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(Profile_details.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(Profile_details.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
