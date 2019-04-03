package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {

    TextView FName;
    TextView LName;
    TextView UName;
    TextView Email;
    TextView Password;
    private DatabaseReference databaseReference;
    String email;
    ListView listView;

    String[] Articles = {"Jacob Zuma","Wits University","Elections","Load Shedding","Champions League","University Graduation"};
    String[] ArticlesAuthor = {"1 Jan 2018","2 Feb 2018","3 Mar 2018","4 Apr 2018","5 May 2018","6 Jun 2019"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        listView =(ListView)findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        Toast.makeText(HomePage.this,"Entered" ,Toast.LENGTH_LONG).show();

     /**/
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
          return Articles.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View convertView1 = layoutInflater.inflate(R.layout.articlelayout,parent,false);

             //convertView = getLayoutInflater().inflate(R.layout.articlelayout,null);

            //ImageView imageView = convertView1.findViewById(R.id.ArticleImage);
            TextView textView_heading = convertView1.findViewById(R.id.ArticleHeading);
            TextView textView_author = convertView1 .findViewById(R.id.ArticleAuthor);

            //imageView.setImageResource(IMAGES[position]);
            textView_heading.setText(Articles[position]);
            textView_author.setText(ArticlesAuthor [position]);
            return convertView1;


        }
    }

}


/*
    Intent intent = getIntent();
        email = intent.getStringExtra("Email");

                databaseReference = FirebaseDatabase.getInstance().getReference("UserProfile");

                FName = (TextView) findViewById(R.id.tvFirstName);
                LName = (TextView) findViewById(R.id.tvLastName);
                UName = (TextView) findViewById(R.id.tvUserName);
                Email = (TextView) findViewById(R.id.tvEmail);
//FName = (TextView)findViewById(R.id.tvP);
*/

    /*
    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    UserProfile userProfile = artistSnapshot.getValue(UserProfile.class);

                    if (userProfile.getUser_email().equals(email)) {

                        FName.setText(userProfile.getUser_fName());
                        LName.setText(userProfile.getUser_lName());
                        UName.setText(userProfile.getUser_username());
                        Email.setText(userProfile.getUser_email());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
*/
