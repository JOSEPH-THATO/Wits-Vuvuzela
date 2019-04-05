package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ReadArticleActivity extends AppCompatActivity {

    TextView ArticleBody;
    TextView ArticleHeading;
    String urlLink;
    String head;
    String Key;
    String ArticleComments;
    int likes;
    int dislikes;
    DatabaseReference databaseReference;
    ListView viewComments;
    ImageView LikeButton;
    ImageView DislikeButton;
    TextView NumLikes;
    TextView NumDislikes;

    ArrayList<String> CommentsArrayList;

    String[] UserNames = {"Jacob Zuma", "Wits University", "Elections"};
    String[] UserComments = {"State Capture Report", "Best Institution in Africa", "Eskom Crisis"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);

        CommentsArrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Article");

        Bundle bundle = getIntent().getExtras();
        String heading = bundle.getString("Heading");
        head = heading;

        LikeButton = (ImageView)findViewById(R.id.likebtn);
        DislikeButton = (ImageView)findViewById(R.id.dislikebtn);
        NumDislikes = (TextView)findViewById(R.id.dislikeNum);
        NumLikes = (TextView)findViewById(R.id.likeNum);


        ArticleHeading = (TextView) findViewById(R.id.ReadArticleHeading);
        ArticleBody = (TextView) findViewById(R.id.ReadArticleBody);

        ArticleHeading.setText(heading);
        ArticleBody.setText("Article Loading , Please Wait ...");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    Article article = artistSnapshot.getValue(Article.class);

                    if(article.getArticleTitle().equals(head)){

                        String links = article.getArticleLink();
                        String key = databaseReference.push().getKey();
                        String Comments = article.getArticleComments();
                        String Likes = article.getArticleLikes();
                        String Dislikes = article.getArticleDislikes();

                        Key = key;
                        urlLink = links;
                        dislikes = Integer.parseInt(Dislikes);
                        likes = Integer.parseInt(Likes);

                        NumLikes.setText(likes + " Likes");
                        NumDislikes.setText(dislikes + " Dislikes");

                        String[] CommentsArray = Comments.split("/");

                        for(int i = 0;i < CommentsArray.length;++i){

                            CommentsArrayList.add(CommentsArray[i]);

                        }

                        viewComments = (ListView) findViewById(R.id.viewCommentsID);
                        CustomAdapter customAdapter = new CustomAdapter();
                        viewComments.setAdapter(customAdapter);

                        new doit().execute();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //new doit().execute();

        LikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeArticle();
            }
        });

        DislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DislikeArticle();
            }
        });

    }

    public void AddComment(String Comment){

        ArticleComments += ("/" + Comment);

        //Send Comment to database

    }

    public void LikeArticle(){
        likes+=1;
        NumLikes.setText(likes);
    }

    public void DislikeArticle(){
        dislikes+=1;
        NumDislikes.setText(dislikes);
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return CommentsArrayList.size();
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
            View convertView2 = layoutInflater.inflate(R.layout.commentlayout, parent, false);
            TextView txtUserName = convertView2.findViewById(R.id.userEmail);
            TextView txtComment = convertView2.findViewById(R.id.TxtComment);

            txtUserName.setText("None");
            txtComment.setText(CommentsArrayList.get(position));
            return convertView2;
        }
    }

    public class doit extends AsyncTask<Void, Void, Void> {

        ArrayList<String> Heading1;

        String words = "";
        String[] AuthorOrDate;
        String Author = "";

        @Override
        protected Void doInBackground(Void... voids) {

            try {


                Heading1 = new ArrayList<>();

                // String imgURL  = "https://www.google.com/images/srpr/logo11w.png";
                // new DownLoadImageTask(iv).execute(imgURL);
                Document mBlogDocument = Jsoup.connect(urlLink).get();
                Elements mElementDataSize = mBlogDocument.select("div[class=entry-content]");
                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < 10; i++) {

                    Elements mElementArticle = mBlogDocument.select("p").eq(i);
                    String mArticleBody = mElementArticle.text();

                    if(i == 0){
                        Author = mArticleBody;
                    }

                    else if(i == 1){
                        Heading1.add(mArticleBody);
                    }

                    else {
                        Heading1.add(" " + mArticleBody);
                        words += mArticleBody;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArticleBody.setText(words);
        }
    }
}
