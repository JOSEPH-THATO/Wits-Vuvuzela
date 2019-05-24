package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {


    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    ListView listView;
    ArrayList<String> ArticlesBody;
    ArrayList<String> ArticlesTitle;
    String Email="";
    String Key = "";
    ProgressBar HomePageBar;
    String User = "abc";
    Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Intent bundle = getIntent();
        String email =   bundle.getStringExtra("Email");
        Email = email;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Notification");

        notification = new Notification();

        ArticlesBody = new ArrayList<>();
        ArticlesTitle = new ArrayList<>();

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    notification = artistSnapshot.getValue(Notification.class);

                    if (notification.getNotificationUser().equals(Email)) {
                        ArticlesBody.add(notification.getNotificationTitle());
                        ArticlesTitle.add(notification.getNotificationBody());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*listView = (ListView) findViewById(R.id.listview);
        HomePage.CustomAdapter customAdapter = new HomePage.CustomAdapter();

        HomePageBar.setVisibility(View.GONE);

        listView.setAdapter(customAdapter);*/

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ArticlesBody.size();
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

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View convertView1 = layoutInflater.inflate(R.layout.notificationlayout, parent, false);

            TextView textView_title = convertView1.findViewById(R.id.NotificationTitle);
            TextView textView_body = convertView1.findViewById(R.id.NotificationBody);

            textView_title.setText(ArticlesTitle.get(position));
            textView_body.setText(ArticlesTitle.get(position));
            return convertView1;
        }
    }

}
