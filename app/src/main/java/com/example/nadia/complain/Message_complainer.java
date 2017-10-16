package com.example.nadia.complain;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;
import android.support.annotation.NonNull;
import android.widget.TextView;

public class Message_complainer extends AppCompatActivity {

    private Button send;
    private TextView name, phone, message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String TphoneNo;
    String Tmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_complainer);

        send = (Button) findViewById(R.id.com_send);

        name = (TextView) findViewById(R.id.com_name);
        phone = (TextView) findViewById(R.id.com_phone);
        message = (TextView) findViewById(R.id.com_message);

        Bundle i = getIntent().getExtras();
        name.setText(i.getString("com_name"));
        phone.setText(i.getString("com_phone"));
        message.setText("Contact Email:" + i.getString("police_email") + "\n");

    }


    public void onClick(View v) {
        if (v == send) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                My_Message();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

    }

    private void My_Message() {
        SmsManager smsManager = SmsManager.getDefault();
        TphoneNo = getIntent().getExtras().getString("com_phone");
        Tmessage = message.getText().toString().trim();
        smsManager.sendTextMessage(TphoneNo, null, Tmessage, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        finish();
        Intent i = new Intent(this,Profile.class);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    My_Message();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                }
            }
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

                startActivity(new Intent(Message_complainer.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(Message_complainer.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(Message_complainer.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(Message_complainer.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(Message_complainer.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

