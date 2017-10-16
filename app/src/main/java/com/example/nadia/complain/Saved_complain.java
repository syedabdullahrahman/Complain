package com.example.nadia.complain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Saved_complain extends AppCompatActivity implements View.OnClickListener{


    ListView listViewComplains;
    DatabaseReference databaseCompList;
    List<ComplainBox> complainBoxList;
    private Button logout;
    private TextView total;
    private FirebaseAuth firebaseAuth;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_complain);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth==null)
        {
            finish();
            Intent i = new Intent(this,Login.class);
            startActivity(i);
        }
        count=0;
        complainBoxList = new ArrayList<>();
        databaseCompList = FirebaseDatabase.getInstance().getReference("Saved_Complain");

        listViewComplains = (ListView) findViewById(R.id.listViewComplains);
        total = (TextView)findViewById(R.id.total_complain);

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
                    count++;
                    ComplainBox co = complainSnapshot.getValue(ComplainBox.class);
                    complainBoxList.add(co);
                }
                ComplainList adapter = new ComplainList(Saved_complain.this, complainBoxList);
                listViewComplains.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listViewComplains.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ComplainBox c = complainBoxList.get((int) id);
                Intent i = new Intent(Saved_complain.this, Complain_details.class);
                i.putExtra("complain_id", c.getIdC());
                Saved_complain.this.startActivity(i);
                //Toast.makeText(getApplicationContext(),"Item Clicked: pos:"+position+ " id: "+c.getIdC(),Toast.LENGTH_SHORT).show();
            }
        });
        total.setText(String.valueOf(complainBoxList.size()));
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

                startActivity(new Intent(Saved_complain.this,MainActivity.class));
                return true;

            case R.id.complain_menu :

                startActivity(new Intent(Saved_complain.this,Complain.class));
                return true;

            case R.id.author_menu :

                startActivity(new Intent(Saved_complain.this,Author.class));
                return  true;

            case R.id.about_us:

                startActivity(new Intent(Saved_complain.this,About_app.class));
                return true;

            case R.id.contact_us:

                startActivity(new Intent(Saved_complain.this,Contact.class));
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View view) {

    }
}
