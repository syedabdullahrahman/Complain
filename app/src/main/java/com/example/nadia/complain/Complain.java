package com.example.nadia.complain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Complain extends AppCompatActivity implements View.OnClickListener {

    private EditText name, phone, nationalId, postcode, address, compl;

    private Button submit;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        submit = (Button) findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.nameet);
        phone = (EditText) findViewById(R.id.phoneet);
        nationalId = (EditText) findViewById(R.id.nationalIdet);
        postcode = (EditText) findViewById(R.id.postcodeet);
        address = (EditText) findViewById(R.id.addresset);
        compl = (EditText) findViewById(R.id.complainet);

        databaseReference = FirebaseDatabase.getInstance().getReference("Complain");
        progressDialog = new ProgressDialog(this);
        submit.setOnClickListener(this);


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

                startActivity(new Intent(Complain.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(Complain.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(Complain.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(Complain.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(Complain.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        if (view == submit) {
            saveComplain();
        }
    }

    private void saveComplain() {
        String nam = name.getText().toString().trim();
        String phn = phone.getText().toString().trim();
        String nid = nationalId.getText().toString().trim();
        String pcode = postcode.getText().toString().trim();
        String adrs = address.getText().toString().trim();
        String comp = compl.getText().toString().trim();

        if (TextUtils.isEmpty(nam)) {
            Toast.makeText(this, "Enter your name to proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phn)) {
            Toast.makeText(this, "Enter your phone number to proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nid)) {
            Toast.makeText(this, "Enter your national id to proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pcode)) {
            Toast.makeText(this, "Enter your postcode to proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(adrs)) {
            Toast.makeText(this, "Enter your address to proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(comp)) {
            Toast.makeText(this, "Enter your complain to submit", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Submitting Complain. Please wait......");
        progressDialog.show();

        if (!TextUtils.isEmpty(comp)) {
            String id = databaseReference.push().getKey();
            ComplainBox c = new ComplainBox(id, nam, phn, nid, pcode, adrs, comp);
            databaseReference.child(id).setValue(c);
            progressDialog.hide();
            Toast.makeText(this, "Complain submitted successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Complain has not been submitted! Try again", Toast.LENGTH_SHORT).show();
        }

    }
}
