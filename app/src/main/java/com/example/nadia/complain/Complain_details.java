package com.example.nadia.complain;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.button;

public class Complain_details extends AppCompatActivity implements View.OnClickListener {

    String phn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;
    private Button message, call, save, delete;
    private TextView name, phone, national_id, post_code, complain, address;
    private ComplainBox cm;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_details);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth == null) {
            finish();
            startActivity(new Intent(this, Login.class));
        }

        name = (TextView) findViewById(R.id.com_name);
        phone = (TextView) findViewById(R.id.com_phone);
        national_id = (TextView) findViewById(R.id.com_national_Id);
        post_code = (TextView) findViewById(R.id.com_post_code);
        complain = (TextView) findViewById(R.id.com_complain);
        address = (TextView) findViewById(R.id.com_address);
        message = (Button) findViewById(R.id.com_message);
        call = (Button) findViewById(R.id.com_call);
        save = (Button) findViewById(R.id.com_save);
        delete = (Button) findViewById(R.id.com_delete);

        save.setOnClickListener(this);
        delete.setOnClickListener(this);
        message.setOnClickListener(this);
        call.setOnClickListener(this);

        user = firebaseAuth.getCurrentUser();
        Bundle extra = getIntent().getExtras();
        String c_id = extra.getString("complain_id");
        database = FirebaseDatabase.getInstance().getReference().child("Complain").child(c_id);

        ValueEventListener post = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                cm = dataSnapshot.getValue(ComplainBox.class);
                name.setText(cm.getNameC());
                phone.setText(cm.getPhoneC());
                national_id.setText(cm.getNid());
                post_code.setText(cm.getPostcodeC());
                complain.setText(cm.getCmpln());
                address.setText(cm.getAddress());
                phn=cm.getPhoneC();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database.addListenerForSingleValueEvent(post);
    }

    @Override
    public void onClick(View v) {
        if (v == message) {
            Intent i = new Intent(Complain_details.this, Message_complainer.class);
            i.putExtra("com_name", cm.getNameC());
            i.putExtra("com_phone", cm.getPhoneC());
            i.putExtra("police_id", user.getUid());
            i.putExtra("police_email", user.getEmail());
            Complain_details.this.startActivity(i);
        }
        if (v == call) {
            //call korbe
            String p = "tel:"+phn;
            Intent callintent = new Intent(Intent.ACTION_DIAL);
            callintent.setData(Uri.parse(p));
            startActivity(callintent);
        }
        if (v == save) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure,You wanted to save this complain ?");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Bundle extra = getIntent().getExtras();
                            String c_id = extra.getString("complain_id");
                            database = FirebaseDatabase.getInstance().getReference().child("Saved_Complain");
                            database.child(c_id).setValue(cm);
                            Toast.makeText(getApplicationContext(), "Complain Saved Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Complain_details.this, Profile.class);
                            startActivity(i);
                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        if (v == delete) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure,You wanted to delete this complain ?");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Bundle extra = getIntent().getExtras();
                            String c_id = extra.getString("complain_id");
                            database = FirebaseDatabase.getInstance().getReference().child("Complain");
                            database.child(c_id).removeValue();
                            Toast.makeText(getApplicationContext(), "Complain Deleted Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Complain_details.this, Profile.class);
                            startActivity(i);
                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
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

                startActivity(new Intent(Complain_details.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(Complain_details.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(Complain_details.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(Complain_details.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(Complain_details.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
