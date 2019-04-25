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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;
import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    EditText EditComment;
    ImageView CommentButton;
    ListView viewComments;
    Article article;
    ArrayList<String> CommentsArrayList;
    ArrayList<String> NamesArrayList;
    private String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Bundle bundle = getIntent().getExtras();
        String ArticleComments = bundle.getString("ArticleComments");

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
        viewComments = (ListView) findViewById(R.id.viewCommentsID);
        CustomAdapter customAdapter = new CustomAdapter();
        viewComments.setAdapter(customAdapter);

        CommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NewComment = EditComment.getText().toString().trim();

                if (!NewComment.equals("")) {
                    AddComment(article, NewComment);
                }
            }
        });
    }


    private void AddComment(Article article,String Comment){

        DatabaseReference databaseReference4;

        article.AddComment(Comment,Email);

        databaseReference4 = FirebaseDatabase.getInstance().getReference("Article").child(key);
        databaseReference4.child("articleComments").setValue(article.getArticleComments());

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
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
            //  textView_heading.setText(ArticlesHead.get(position));
            // textView_author.setText("By " + ArticlesAuth.get(position));
            return convertView1;

        }
    }
}