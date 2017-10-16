package com.example.nadia.complain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    //define all the view objects
    private EditText email;
    private EditText password;
    private EditText phoneNo;
    private EditText name;
    private EditText rank;
    private EditText postcode;
    private EditText policeid;
    private Button signUp;

    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    // defining FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        signUp = (Button) findViewById(R.id.signup);


        //initializing all the views
        email = (EditText) findViewById(R.id.emailet);
        password = (EditText) findViewById(R.id.passwordet);
        name = (EditText) findViewById(R.id.nameet);
        rank = (EditText) findViewById(R.id.ranket);
        postcode = (EditText) findViewById(R.id.postcodeet);
        policeid = (EditText) findViewById(R.id.policeIdet);
        phoneNo = (EditText) findViewById(R.id.phoneet);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button sign up
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == signUp) {
            //Toast.makeText(this, "sign button pressed", Toast.LENGTH_SHORT).show();
            registerUser();
        }
    }

    private void saveUserInfo(String em1, String pass1, String nam1, String phn1, String postCode1, String policeId1, String rak1) {

        if (!TextUtils.isEmpty(em1)) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            final String uid = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("AuthorProfile");
            UserInformation uinfo = new UserInformation(nam1, em1, phn1, policeId1, rak1, postCode1, pass1);
            databaseReference.child(uid).setValue(uinfo);

        } else {
            Toast.makeText(this, "Data have not been saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {

        //getting all the inputs from the user
        final String em = email.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String nam = name.getText().toString().trim();
        final String phn = phoneNo.getText().toString().trim();
        final String postCode = postcode.getText().toString().trim();
        final String policeId = policeid.getText().toString().trim();
        final String rak = rank.getText().toString().trim();


        //validation
        if (TextUtils.isEmpty(nam)) {
            Toast.makeText(this, "Please enter name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(em)) {
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phn)) {
            Toast.makeText(this, "Please enter phone number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(policeId)) {
            Toast.makeText(this, "Please enter police id!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(rak)) {
            Toast.makeText(this, "Please enter rank!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(postCode)) {
            Toast.makeText(this, "Please enter postcode!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() <= 5) {
            Toast.makeText(this, "Password must be more than 5 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering, Please Wait...");
        progressDialog.show();

        //now we can create the user
        firebaseAuth.createUserWithEmailAndPassword(em, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveUserInfo(em, pass, nam, phn, postCode, policeId, rak);
                            Toast.makeText(Registration.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(Registration.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(Registration.this, "Something went terrible wrong!", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    }
                });
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

                startActivity(new Intent(Registration.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(Registration.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(Registration.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(Registration.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(Registration.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

