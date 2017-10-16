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

public class Login extends AppCompatActivity implements View.OnClickListener {

    //define all the views
    private Button signin;
    private EditText email;
    private EditText password;

    private ProgressDialog progressDialog;

    // defining FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //if someone is already logged in
        if (firebaseAuth.getCurrentUser() != null) {
            finish(); // then close this activity
            startActivity(new Intent(Login.this, Profile.class)); // and go to their profile page
        }

        //initializing all the views
        email = (EditText) findViewById(R.id.emailetI);
        password = (EditText) findViewById(R.id.passwordetI);

        signin = (Button) findViewById(R.id.signinbt);

        progressDialog = new ProgressDialog(this);

        signin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == signin) {
            userLogin();
        }
    }

    private void userLogin() {

        //getting all the inputs from the user
        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        //validation
        if (TextUtils.isEmpty(em)) {
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() <= 5) {
            Toast.makeText(this, "Password must be more than 5 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Signing in, Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(em, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            progressDialog.hide();
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        } else {
                            Toast.makeText(Login.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
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

                startActivity(new Intent(Login.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(Login.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(Login.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(Login.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(Login.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
