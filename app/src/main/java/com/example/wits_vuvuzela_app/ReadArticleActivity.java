package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    int likes=0;
    int dislikes=0;
    DatabaseReference databaseReference;
    ListView viewComments;
    ImageView LikeButton;
    ImageView DislikeButton;
    TextView NumLikes;
    TextView NumDislikes;
    EditText EditComment;
    ImageView CommentButton;
    Article article;
    String Email="";
    ProgressBar ArticleBar;

    ArrayList<String> CommentsArrayList;
    ArrayList<String> NamesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        SetUpUI();

        databaseReference = FirebaseDatabase.getInstance().getReference("Article");

        Bundle bundle = getIntent().getExtras();
        String heading = bundle.getString("Heading");
        String email = bundle.getString("Email");
        Email = email;
        head = heading;

        ArticleHeading.setText(heading);
        ArticleBody.setText("Article Loading , Please Wait ...");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    article = artistSnapshot.getValue(Article.class);

                    if(article.getArticleTitle().equals(head)){

                        String links = article.getArticleLink();
                        String key = artistSnapshot.getKey();
                        String Comments = article.getArticleComments();
                        String Likes = article.getArticleLikes();
                        String Dislikes = article.getArticleDislikes();

                        if(article.ArticleAlreadyLiked(Email) && article.ArticleAlreadyDisliked(Email)){
                            LikeButton.setImageResource(R.drawable.likebw);
                            DislikeButton.setImageResource(R.drawable.dislikebw);
                        }

                        else if(article.ArticleAlreadyLiked(Email)){
                            LikeButton.setImageResource(R.drawable.like);
                        }

                        else if(article.ArticleAlreadyDisliked(Email)){
                            DislikeButton.setImageResource(R.drawable.dislike);
                        }

                        Key = key;
                        urlLink = links;
                        dislikes = Integer.parseInt(Dislikes);
                        likes = Integer.parseInt(Likes);

                        NumLikes.setText(likes + " Likes");
                        NumDislikes.setText(dislikes + " Dislikes");

                        CommentsArrayList = new ArrayList<>();
                        NamesArrayList = new ArrayList<>();

                        String[] CommentsArray = Comments.split("/");

                        for(int i = 0;i < CommentsArray.length;++i){

                            String[] NamesArray = CommentsArray[i].split("-");

                            String name = NamesArray[0];
                            String comment = NamesArray[1];

                            CommentsArrayList.add(comment);
                            NamesArrayList.add(name);
                        }
                        viewComments = (ListView) findViewById(R.id.viewCommentsID);
                        CustomAdapter customAdapter = new CustomAdapter();
                        viewComments.setAdapter(customAdapter);

                        CommentButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AddComment(article,EditComment.getText().toString().trim());
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

    public void AddComment(Article article,String Comment){

        DatabaseReference databaseReference4;

        article.AddComment(Comment,Email);

        databaseReference4 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
        databaseReference4.child("articleComments").setValue(article.getArticleComments());

    }

    public void LikeArticle(Article article,String User){

        DatabaseReference databaseReference5;

        article.LikeAnArticle(User);

        databaseReference5 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
        databaseReference5.child("articleLikes").setValue(article.getArticleLikes());
        databaseReference5.child("articleLikedList").setValue(article.getArticleLikedList());
        databaseReference5.child("articleDislikes").setValue(article.getArticleDislikes());

    }

    public void DislikeArticle(Article article,String User){

        DatabaseReference databaseReference6;

        article.DislikeAnArticle(User);

        databaseReference6 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
        databaseReference6.child("articleDislikes").setValue(article.getArticleDislikes());
        databaseReference6.child("articleDislikedList").setValue(article.getArticleDislikedList());
        databaseReference6.child("articleLikes").setValue(article.getArticleLikes());

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

            txtUserName.setText(NamesArrayList.get(position));
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
            ArticleBar.setVisibility(View.GONE);
            ArticleBody.setText(words);
        }
    }

    public void SetUpUI(){

        EditComment = (EditText)findViewById(R.id.editComment);
        CommentButton = (ImageView)findViewById(R.id.commentBtn);
        LikeButton = (ImageView)findViewById(R.id.likebtn);
        DislikeButton = (ImageView)findViewById(R.id.dislikebtn);
        NumDislikes = (TextView)findViewById(R.id.dislikeNum);
        NumLikes = (TextView)findViewById(R.id.likeNum);
        ArticleHeading = (TextView) findViewById(R.id.ReadArticleHeading);
        ArticleBody = (TextView) findViewById(R.id.ReadArticleBody);
        ArticleBar = (ProgressBar)findViewById(R.id.ArticleBar);
    }
}


