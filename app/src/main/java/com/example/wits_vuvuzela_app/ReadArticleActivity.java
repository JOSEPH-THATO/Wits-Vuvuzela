package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    String UrlLink;
    String head;
    String Key;
    int likes=0;
    int dislikes=0;
    String LikedListS="";
    String DislikedListS="";
    DatabaseReference databaseReference;
    ListView viewComments;
    ImageView LikeButton;
    ImageView DislikeButton;
    TextView NumLikes;
    TextView NumDislikes;
    EditText EditComment;
    ImageView CommentButton;
    Article article;
    Rating rating;
    String Email="";
    ProgressBar ArticleBar;
    TextView tvComment;
    LinearLayout likedislikeLayout;
    LinearLayout commentLayout;
    ArrayList<String> CommentsArrayList;
    ArrayList<String> NamesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample3);

        Bundle bundle = getIntent().getExtras();
        String heading = bundle.getString("Heading");
        String email = bundle.getString("Email");
        Email = email;
        head = heading;

        SetUpUI();

        //likedislikeLayout.setVisibility(View.GONE);

        databaseReference = FirebaseDatabase.getInstance().getReference("Article");
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
                        String LikedList = article.getArticleLikedList();
                        String DislikedList = article.getArticleDislikedList();

                        if(!article.ArticleAlreadyLiked(Email,LikedList) && !article.ArticleAlreadyDisliked(Email,DislikedList)){
                            LikeButton.setImageResource(R.drawable.likebw);
                            DislikeButton.setImageResource(R.drawable.dislikebw);
                        }


                        else if(article.ArticleAlreadyLiked(Email,LikedList)){
                            LikeButton.setImageResource(R.drawable.like);
                            DislikeButton.setImageResource(R.drawable.dislikebw);
                        }

                        else if(article.ArticleAlreadyDisliked(Email,DislikedList)){
                            DislikeButton.setImageResource(R.drawable.dislike);
                            LikeButton.setImageResource(R.drawable.likebw);
                        }

                        Key = key;
                        UrlLink = links;
                        dislikes = Integer.parseInt(Dislikes);
                        likes = Integer.parseInt(Likes);
                        LikedListS = LikedList;
                        DislikedListS = DislikedList;

                        NumLikes.setText(likes + " Likes");
                        NumDislikes.setText(dislikes + " Dislikes");

                        //likedislikeLayout.setVisibility(View.VISIBLE);
                        //commentLayout.setVisibility(View.VISIBLE);

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

                        //viewComments = (ListView) findViewById(R.id.viewCommentsID);
                        //CustomAdapter customAdapter = new CustomAdapter();
                        //viewComments.setAdapter(customAdapter);

                        /*CommentButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String NewComment = EditComment.getText().toString().trim();

                                if(!NewComment.equals("")) {
                                    AddComment(article, NewComment);
                                }
                                commentLayout.setVisibility(View.GONE);
                            }
                        });*/

                        LikeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LikeArticle(article, Email);
                                    //likedislikeLayout.setVisibility(View.GONE);
                                }
                        });

                        DislikeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DislikeArticle(article, Email);
                                    //likedislikeLayout.setVisibility(View.GONE);
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

    public void setLikeDislike(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                    rating = artistSnapshot.getValue(Rating.class);
                    String key = artistSnapshot.getKey();

                    if(rating.getArticleID().equals(key)){
                        if(rating.getLike().equals("Yes")){
                            LikeButton.setImageResource(R.drawable.like);
                        }
                        if(rating.getLike().equals("No")){
                            LikeButton.setImageResource(R.drawable.likebw);
                        }
                        if(rating.getDislike().equals("Yes")){
                            DislikeButton.setImageResource(R.drawable.dislike);
                        }
                        if(rating.getDislike().equals("No")){
                            DislikeButton.setImageResource(R.drawable.dislikebw);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void AddComment(Article article,String Comment){

        DatabaseReference databaseReference4;

        article.AddComment(Comment,Email);

        databaseReference4 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
        databaseReference4.child("articleComments").setValue(article.getArticleComments());

    }

    public void LikeArticle(Article article,String User){

        DatabaseReference databaseReference5;

        article.LikeAnArticle(User,likes,dislikes,LikedListS,DislikedListS);

        databaseReference5 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
        databaseReference5.child("articleLikes").setValue(article.getArticleLikes());
        databaseReference5.child("articleLikedList").setValue(article.getArticleLikedList());
        databaseReference5.child("articleDislikedList").setValue(article.getArticleDislikedList());
        databaseReference5.child("articleDislikes").setValue(article.getArticleDislikes());

    }


    public void DislikeArticle(Article article,String User){

        DatabaseReference databaseReference6;

        article.DislikeAnArticle(User,likes,dislikes,LikedListS,DislikedListS);

        databaseReference6 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
        databaseReference6.child("articleDislikes").setValue(article.getArticleDislikes());
        databaseReference6.child("articleDislikedList").setValue(article.getArticleDislikedList());
        databaseReference6.child("articleLikedList").setValue(article.getArticleLikedList());
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

            txtUserName.setText("ab");
            txtComment.setText(CommentsArrayList.get(position));
            return convertView2;
        }
    }

    public class doit extends AsyncTask<Void, Void, Void> {

        ArrayList<String> Heading1;

        String words = "";
        String Author = "";

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Heading1 = new ArrayList<>();

                // String imgURL  = "https://www.google.com/images/srpr/logo11w.png";
                // new DownLoadImageTask(iv).execute(imgURL);
                Document mBlogDocument = Jsoup.connect(UrlLink).get();
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

    private void SetUpUI(){

        //EditComment = (EditText)findViewById(R.id.editComment);
        //CommentButton = (ImageView)findViewById(R.id.commentBtn);
        LikeButton = (ImageView)findViewById(R.id.likebtn);
        DislikeButton = (ImageView)findViewById(R.id.dislikebtn);
        NumDislikes = (TextView)findViewById(R.id.dislikeNum);
        NumLikes = (TextView)findViewById(R.id.likeNum);
        ArticleHeading = (TextView) findViewById(R.id.ReadArticleHeading);
        ArticleBody = (TextView) findViewById(R.id.ReadArticleBody);
        ArticleBar = (ProgressBar)findViewById(R.id.ArticleBar);
        likedislikeLayout = (LinearLayout)findViewById(R.id.likedislikeLayout);
        //commentLayout = (LinearLayout)findViewById(R.id.Commentlayout);
        //tvComment = (TextView) findViewById(R.id.tComment);
    }
}


