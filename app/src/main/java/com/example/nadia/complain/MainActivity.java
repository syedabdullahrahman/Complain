package com.example.nadia.complain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button comp, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comp = (Button) findViewById(R.id.complaintext);
        author = (Button) findViewById(R.id.authortext);

        comp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Complain.class);
                startActivity(intent);
            }
        });
        author.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Author.class);
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

                startActivity(new Intent(MainActivity.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(MainActivity.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(MainActivity.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(MainActivity.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(MainActivity.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
