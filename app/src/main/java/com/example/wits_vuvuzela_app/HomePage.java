package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class HomePage extends AppCompatActivity {

    FirebaseAuth firebaseauth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    Article article;
    ListView listView;
    ArrayList<String> ArticlesHead;
    ArrayList<String> ArticlesAuth;
    ArrayList<String> ArticlesBit;
    ArrayList<String> ArticlesLink;
    ArrayList<String> ArticlesImgSrc;
    String SendArticleHeading;
    String Email="";
    ProgressBar HomePageBar;
    String User = "abc";
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        HomePageBar = (ProgressBar)findViewById(R.id.HomePageBar);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("Email");
        Email = email;


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Article");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("UserProfile");
        firebaseauth = FirebaseAuth.getInstance();
        article = new Article();

        ArticlesHead = new ArrayList<>();
        ArticlesAuth = new ArrayList<>();
        ArticlesLink = new ArrayList<>();
        ArticlesImgSrc = new ArrayList<>();
        ArticlesBit = new ArrayList<>();


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    userProfile = artistSnapshot.getValue(UserProfile.class);

                    if (userProfile.getUser_email().equals(Email)) {
                        User = userProfile.getUser_username();
                        break;
                    }
                }

                Toast.makeText(HomePage.this, "Blessings " + User, Toast.LENGTH_LONG).show();

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

            new DownLoadImageTask(imageView).execute(ArticlesImgSrc.get(position));

            //imageView.setImageResource(IMAGES[position]);
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
        ArrayList<String> Bit1;


        String words = "";

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                //Toast.makeText(HomePage.this, "Blessings ", Toast.LENGTH_LONG).show();


                Heading1 = new ArrayList<>();
                Author1 = new ArrayList<>();
                Link1 = new ArrayList<>();
                ImgSrc1 = new ArrayList<>();
                Bit1 = new ArrayList<>();

                Document mBlogDocument = Jsoup.connect("https://witsvuvuzela.com").get();
                Elements mElementDataSize = mBlogDocument.select("div[class=el-dbe-blog-extra block_extended]");
                int mElementSize = mElementDataSize.size();

               //  Toast.makeText(HomePage.this, mElementSize+" sent", Toast.LENGTH_LONG).show();

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

                    //  words+="\n";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Toast.makeText(HomePage.this, "Blessings ", Toast.LENGTH_LONG).show();

            ArticlesAuth = Author1;
            ArticlesHead = Heading1;
            ArticlesLink = Link1;
            ArticlesImgSrc = ImgSrc1;

            listView = (ListView) findViewById(R.id.listview);
            CustomAdapter customAdapter = new CustomAdapter();

            HomePageBar.setVisibility(View.GONE);

            listView.setAdapter(customAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(HomePage.this, ReadArticleActivity.class);
                    intent.putExtra("Heading", ArticlesHead.get(position));
                    intent.putExtra("Email", Email);
                    SendArticle(ArticlesHead.get(position), ArticlesLink.get(position),ArticlesImgSrc.get(position));
                    startActivity(intent);
                }
            });
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



    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap>{
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);

        }
    }

    @Override
    public void onBackPressed(){

        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
    }

}