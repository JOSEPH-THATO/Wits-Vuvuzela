package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class ReadArticleActivity extends AppCompatActivity {


    TextView ArticleBody;
    TextView ArticleHeading;
    String UrlLink;
    String head;
    String Key;
    int likes=0;
    int dislikes=0;
    int NoReplies = 0;
    DatabaseReference databaseReference;
    ImageView LikeButton;
    ImageView DislikeButton;
    TextView NumLikes;
    TextView NumDislikes;
    TextView NumComments;
    ImageView CommentIconButton;
    Article article;
    String Email="";
    ProgressBar ArticleBar;
    ImageView ArticleImg;
    Bitmap ImageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);

        Bundle bundle = getIntent().getExtras();
        String heading = bundle.getString("Heading");
        String email = bundle.getString("Email");
        Email = email;
        head = heading;

        SetUpUI();

        ArticleImg.setVisibility(View.GONE);

        databaseReference = FirebaseDatabase.getInstance().getReference("Article");

        ArticleHeading.setText(heading);
        ArticleBody.setText("Article Loading , Please Wait ...");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    article = artistSnapshot.getValue(Article.class);

                    if(article.getArticleTitle().equals(head)){

                        String key = artistSnapshot.getKey();

                        new GetArticleImage(article.getArticleImage()).execute();

                        if(article.getArticleLikedList().contains(Email)){
                            LikeButton.setImageResource(R.drawable.like);
                            DislikeButton.setImageResource(R.drawable.dislikebw);
                        }

                        else if(article.getArticleDislikedList().contains(Email)){
                            DislikeButton.setImageResource(R.drawable.dislike);
                            LikeButton.setImageResource(R.drawable.likebw);
                        }

                        //if(!article.getArticleLikedList().contains(Email) && !article.getArticleDislikedList().contains(Email)){
                        else{
                            LikeButton.setImageResource(R.drawable.likebw);
                            DislikeButton.setImageResource(R.drawable.dislikebw);
                        }

                        Key = key;
                        UrlLink = article.getArticleLink();
                        dislikes = Integer.parseInt(article.getArticleDislikes());
                        likes = Integer.parseInt(article.getArticleLikes());
                        NoReplies = Integer.parseInt(article.getNoArticleReplies());

                        NumLikes.setText(likes + " Likes");
                        NumDislikes.setText(dislikes + " Dislikes");
                        NumComments.setText(String.valueOf(NoReplies));

                        CommentIconButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GoToCommentsPage();
                            }
                        });

                        LikeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LikeArticle(article, Email);
                                }
                        });

                        DislikeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DislikeArticle(article, Email);
                                }
                        });

                        new doit().execute();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void GoToCommentsPage() {

        Intent intent = new Intent(ReadArticleActivity.this, CommentsActivity.class);
        intent.putExtra("Key", Key);
        intent.putExtra("Email", Email);
        intent.putExtra("CommentsTitle", head);
        intent.putExtra("NumberReplies", NoReplies );
        intent.putExtra("CommentType", "Article" );
        startActivity(intent);

    }

    private void LikeArticle(Article article,String User){

        DatabaseReference databaseReference5;

        article.LikeAnArticle(User);

        databaseReference5 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
        databaseReference5.child("articleLikes").setValue(article.getArticleLikes());
        databaseReference5.child("articleLikedList").setValue(article.getArticleLikedList());
        databaseReference5.child("articleDislikedList").setValue(article.getArticleDislikedList());
        databaseReference5.child("articleDislikes").setValue(article.getArticleDislikes());

    }

    private void DislikeArticle(Article article,String User){

        DatabaseReference databaseReference6;

        article.DislikeAnArticle(User);

        databaseReference6 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
        databaseReference6.child("articleDislikes").setValue(article.getArticleDislikes());
        databaseReference6.child("articleDislikedList").setValue(article.getArticleDislikedList());
        databaseReference6.child("articleLikedList").setValue(article.getArticleLikedList());
        databaseReference6.child("articleLikes").setValue(article.getArticleLikes());

    }

    public class doit extends AsyncTask<Void, Void, Void> {

        String words = "";
        String Author = "";

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document mBlogDocument = Jsoup.connect(UrlLink).get();
               // Elements mElementDataSize = mBlogDocument.select("div[class=entry-content]");

                int size = 0;
                Elements paragraphs = mBlogDocument.select("p");
                for (Element p : paragraphs) {
                    size+=1;
                }

                for (int i = 0; i < size-5; i++) {

                    Elements mElementArticle = mBlogDocument.select("p").eq(i);
                    String mArticleBody = mElementArticle.text();

                    if(i == 0){
                        Author = mArticleBody;
                    }

                    else if(i == 1){
                        //articledate
                    }

                    else {
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
            ArticleBar.setVisibility(View.GONE);
            ArticleBody.setText(words);
        }
    }

    private void SetUpUI(){

        CommentIconButton = (ImageView)findViewById(R.id.commentIconBtn);
        LikeButton = (ImageView)findViewById(R.id.likebtn);
        DislikeButton = (ImageView)findViewById(R.id.dislikebtn);
        NumDislikes = (TextView)findViewById(R.id.dislikeNum);
        NumLikes = (TextView)findViewById(R.id.likeNum);
        NumComments = (TextView)findViewById(R.id.commentANum);
        ArticleHeading = (TextView) findViewById(R.id.ReadArticleHeading);
        ArticleBody = (TextView) findViewById(R.id.ReadArticleBody);
        ArticleBar = (ProgressBar)findViewById(R.id.ArticleBar);
        ArticleImg = (ImageView) findViewById(R.id.ArticleImageView);
    }

    public class GetArticleImage extends AsyncTask<Void, Void, Void> {

        Bitmap logo = null;
        String Url = "";

        public GetArticleImage(String url1) {
            Url = url1;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(Url);
                logo = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ImageUrl = logo;
            ArticleImg.setImageBitmap(ImageUrl);
            ArticleImg.setVisibility(View.VISIBLE);
        }
    }
}


