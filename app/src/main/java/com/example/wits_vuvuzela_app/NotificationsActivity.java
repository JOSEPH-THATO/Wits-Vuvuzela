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

    DatabaseReference databaseReferencess;
    private ListView listView;
    ArrayList<String> ArticlesBody;
    ArrayList<String> ArticlesTitle;
    String Email="";
    ProgressBar HomePageBar;
    Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Intent bundle = getIntent();
        String email = bundle.getStringExtra("email");
        Email = email;

        Toast.makeText(NotificationsActivity.this,"email = " + Email ,Toast.LENGTH_SHORT).show();

        databaseReferencess = FirebaseDatabase.getInstance().getReference().child("Notification");

        HomePageBar = (ProgressBar)findViewById(R.id.HomePageBar1);
        HomePageBar.setVisibility(View.VISIBLE);

        notification = new Notification();

        ArticlesBody = new ArrayList<>();
        ArticlesTitle = new ArrayList<>();

        databaseReferencess.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    notification = new Notification();

                    notification = artistSnapshot.getValue(Notification.class);

                    Toast.makeText(NotificationsActivity.this,"email n= " + notification.getNotificationTo() ,Toast.LENGTH_SHORT).show();

                    if (notification.getNotificationTo().contains(Email)) {
                       // Toast.makeText(NotificationsActivity.this,"email j= " +  ,Toast.LENGTH_SHORT).show();

                        ArticlesBody.add(notification.getNotificationBody());
                        ArticlesTitle.add(notification.getNotificationTitle());
                    }
                    Toast.makeText(NotificationsActivity.this,"email hb= " + Email ,Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView = (ListView) findViewById(R.id.listview2);
        CustomAdapter customAdapter = new CustomAdapter();
        HomePageBar.setVisibility(View.GONE);
        listView.setAdapter(customAdapter);

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
            textView_body.setText(ArticlesBody.get(position));
            return convertView1;
        }
    }

}
