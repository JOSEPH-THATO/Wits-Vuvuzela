package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuth;
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

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class HomePage extends AppCompatActivity {

    TextView txtDateTime;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    Article article;
    ListView listView;
    ArrayList<String> ArticlesHead;
    ArrayList<String> ArticlesAuth;
    ArrayList<String> ArticlesLink;
    ArrayList<String> ArticlesImgSrc;
    String SendArticleHeading;
    String Email="";
    String Key = "";
    ProgressBar HomePageBar;
    String User = "abc";
    UserProfile userProfile;
    ArrayList<Bitmap> ArticlesBitmap;
    String Token = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        HomePageBar = (ProgressBar)findViewById(R.id.HomePageBar);

        Intent bundle = getIntent();
        String email =   bundle.getStringExtra("Email");
         Email = email;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Article");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("UserProfile");
        article = new Article();

        ArticlesHead = new ArrayList<>();
        ArticlesAuth = new ArrayList<>();
        ArticlesLink = new ArrayList<>();
        ArticlesImgSrc = new ArrayList<>();
        ArticlesBitmap = new ArrayList<>();

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if(!task.isSuccessful()){
                    return;
                }

                String token = task.getResult().getToken();
                Token = token;

                Toast.makeText(HomePage.this,"token = " + token ,Toast.LENGTH_SHORT).show();

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    userProfile = artistSnapshot.getValue(UserProfile.class);

                    if (userProfile.getUser_email().equals(Email)) {
                        User = userProfile.getUser_username();
                        Key = artistSnapshot.getKey();
                        break;
                    }
                }

               // SaveToken(Token);

                Toast.makeText(HomePage.this, "Welcome ", Toast.LENGTH_LONG).show();

                new doit().execute();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ArticlesHead.size();
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

            View convertView1 = layoutInflater.inflate(R.layout.articlelayout, parent, false);

            ImageView imageView = convertView1.findViewById(R.id.ArticleImage);
            TextView textView_heading = convertView1.findViewById(R.id.ArticleHeading);
            TextView textView_author = convertView1.findViewById(R.id.ArticleAuthor);

            imageView.setImageBitmap(ArticlesBitmap.get(position));
            textView_heading.setText(ArticlesHead.get(position));
            textView_author.setText("By " + ArticlesAuth.get(position));
            return convertView1;
        }
    }

    public class doit extends AsyncTask<Void, Void, Void> {

        ArrayList<String> Heading1;
        ArrayList<String> Author1;
        ArrayList<String> Link1;
        ArrayList<String> ImgSrc1;

        String words = "";

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Heading1 = new ArrayList<>();
                Author1 = new ArrayList<>();
                Link1 = new ArrayList<>();
                ImgSrc1 = new ArrayList<>();

                Document mBlogDocument = Jsoup.connect("https://witsvuvuzela.com").get();
                Elements mElementDataSize = mBlogDocument.select("div[class=el-dbe-blog-extra block_extended]");
                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < 12; i++) {

                    Elements mElementArticle = mBlogDocument.select("h2[class=entry-title]").select("a[href]").eq(i);
                    String mArticleHead = mElementArticle.text();

                    Heading1.add(mArticleHead);
                    words += mArticleHead;

                    Elements mElementAuthorName = mBlogDocument.select("span[class=author vcard]").select("a").eq(i);
                    String mAuthorName = mElementAuthorName.text();

                    Author1.add(mAuthorName);
                    words += mAuthorName;

                    String mArticleLink = mElementArticle.attr("href");
                    Link1.add(mArticleLink);

                    Elements img = mBlogDocument.select("div.post-media-container").select("img").eq(i);
                    String ImgSrc = img.attr("src");
                    ImgSrc1.add(ImgSrc);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ArticlesAuth = Author1;
            ArticlesHead = Heading1;
            ArticlesLink = Link1;
            ArticlesImgSrc = ImgSrc1;

            new GetArticleImage(ArticlesImgSrc).execute();
        }
    }

    public void SendArticle(String ArticleHeading, String ArticleLink, String ArticleImage) {

        article = new Article();
        SendArticleHeading = ArticleHeading;

        article.setArticleTitle(ArticleHeading);
        article.setArticleLink(ArticleLink);
        article.setArticleImage(ArticleImage);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Boolean ArticleExists = false;

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    Article article = artistSnapshot.getValue(Article.class);

                    if (article.getArticleTitle().equals(SendArticleHeading)) {
                        ArticleExists = true;
                    }
                }

                if (ArticleExists.equals(false)) {

                    databaseReference.push().setValue(article);
                    Toast.makeText(HomePage.this, "Article sent", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class GetArticleImage extends AsyncTask<Void, Void, Void> {

        ArrayList<String> Pictures;
        ArrayList<Bitmap> Bit;

        Bitmap logo = null;

        public GetArticleImage(ArrayList<String> pics) {
            Pictures = pics;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Bit = new ArrayList<>();

            try {

                for (int i = 0; i < Pictures.size(); ++i) {
                    URL url = new URL(Pictures.get(i));
                    logo = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Bit.add(logo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArticlesBitmap = Bit;

            listView = (ListView) findViewById(R.id.listview);
            CustomAdapter customAdapter = new CustomAdapter();

            HomePageBar.setVisibility(View.GONE);

            listView.setAdapter(customAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(HomePage.this, ReadArticleActivity.class);
                    intent.putExtra("Heading", ArticlesHead.get(position));
                    intent.putExtra("Email", User);
                    intent.putExtra("Token", Token);
                    SendArticle(ArticlesHead.get(position), ArticlesLink.get(position),ArticlesImgSrc.get(position));
                    startActivity(intent);
                }
            });
        }
    }

    private void SaveToken(String Token){

        DatabaseReference databaseReference88;

        databaseReference88 = FirebaseDatabase.getInstance().getReference("UserProfile").child(Key);
        databaseReference88.child("UserToken").setValue(Token);

    }
}