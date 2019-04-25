package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;
import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    EditText EditComment;
    FirebaseAuth firebaseauth;
    ImageView CommentButton;
    ListView CommentsView;
    Article article;
    DatabaseReference databaseReferenceComments;
    ArrayList<String> CommentsArrayList;
    ArrayList<String> NamesArrayList;
    CommentSection commentSection;
    String Key = "huhu";
    String Email = "huhu";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Bundle bundle = getIntent().getExtras();
        String ArticleComments = bundle.getString("ArticleComments");
        String email = bundle.getString("Email");
        String key = bundle.getString("Key");
        Key = key;
        Email = email;

        databaseReferenceComments = FirebaseDatabase.getInstance().getReference().child("CommentSection");
        firebaseauth = FirebaseAuth.getInstance();

        // databaseReference.push().setValue(userProfile);


        CommentsArrayList = new ArrayList<>();
        NamesArrayList = new ArrayList<>();

        String[] CommentsArray = ArticleComments.split("/");

        for(int i = 0;i < CommentsArray.length;++i){

            String[] NamesArray = CommentsArray[i].split("-");

            String name = NamesArray[0];
            String comment = NamesArray[1];

            CommentsArrayList.add(comment);
            NamesArrayList.add(name);
        }

        EditComment = (EditText) findViewById(R.id.editComment);
        CommentButton = (ImageView) findViewById(R.id.commentBtn);

        CommentsView = (ListView) findViewById(R.id.commentListView);
        CustomAdapter customAdapter1 = new CustomAdapter();
        CommentsView.setAdapter(customAdapter1);

        CommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NewComment = EditComment.getText().toString().trim();

                if (!NewComment.equals("")) {

                    commentSection.setComment(NewComment);
                    databaseReferenceComments.push().setValue(commentSection);
                    //AddComment(article, NewComment);
                }
            }
        });
    }


    private void AddComment(Article article,String Comment){

        DatabaseReference databaseReference4;

        //article.AddComment(Comment,Email);

        //databaseReference4 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
        //databaseReference4.child("articleComments").setValue(article.getArticleComments());

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

            View convertView1 = layoutInflater.inflate(R.layout.articlelayout, parent, false);

            //ImageView imageView = convertView1.findViewById(R.id.ArticleImage);
            TextView textView_heading = convertView1.findViewById(R.id.ArticleHeading);
            TextView textView_author = convertView1.findViewById(R.id.ArticleAuthor);

            //imageView.setImageResource(IMAGES[position]);
             textView_heading.setText(NamesArrayList.get(position));
             textView_author.setText(CommentsArrayList.get(position));
             return convertView1;

        }
    }
}