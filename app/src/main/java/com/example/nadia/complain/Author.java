package com.example.nadia.complain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Author extends AppCompatActivity {

    Button logIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        logIn = (Button) findViewById(R.id.login);
        signUp = (Button) findViewById(R.id.signup);

        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Author.this, Login.class);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Author.this, Registration.class);
                startActivity(intent);
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

                startActivity(new Intent(Author.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(Author.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(Author.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(Author.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(Author.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
