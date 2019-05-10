package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    EditText EditComment;
    ImageView CommentButton;
    private ListView CommentsView;
    DatabaseReference databaseReferenceComments;
    ArrayList<String> CommentsArrayList;
    ArrayList<String> RatesArrayList;
    ArrayList<String> NamesArrayList;
    ArrayList<String> KeysArrayList;
    ArrayList<Integer> NoLikesArrayList;
    ArrayList<Integer> NoDislikesArrayList;
    ArrayList<Integer> NoRepliesArrayList;
    String Key = "huhu";
    String Email = "huhu";
    TextView Article1;
    TextView CommentTitle1;
    CommentSection commentSection;
    ArrayList<String> Keys;
    ArrayList<String> CommentsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Keys = new ArrayList<>();
        CommentsTracker = new ArrayList<>();

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> commentTracker = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("Email");
        String key = bundle.getString("Key");
        String commentTitle = bundle.getString("CommentsTitle");
        keys = bundle.getStringArrayList("Keys");
        commentTracker = bundle.getStringArrayList("CommentsTracker");

        Keys = keys;
        CommentsTracker = commentTracker;

        databaseReferenceComments = FirebaseDatabase.getInstance().getReference().child("CommentSection");

        CommentsArrayList = new ArrayList<>();
        NamesArrayList = new ArrayList<>();
        KeysArrayList = new ArrayList<>();
        RatesArrayList = new ArrayList<>();
        NoDislikesArrayList = new ArrayList<>();
        NoLikesArrayList = new ArrayList<>();
        NoRepliesArrayList = new ArrayList<>();

        CommentsView = (ListView) findViewById(R.id.commentsListView);
        CustomAdapter customAdapter1 = new CustomAdapter();
        CommentsView.setAdapter(customAdapter1);

        Key = key;
        Email = email;

        EditComment = (EditText) findViewById(R.id.editComments);
        CommentButton = (ImageView) findViewById(R.id.commentBtns);
        Article1 = (TextView) findViewById(R.id.title);
        CommentTitle1 = (TextView) findViewById(R.id.CommentTitle);

        CommentTitle1.setText(commentTitle);
        //Article1.setText(Key);

        if(Key.equals("")){
            Toast.makeText(CommentsActivity.this, "No Key Found ", Toast.LENGTH_LONG).show();
        }

        else {

            CommentsArrayList = new ArrayList<>();
            NamesArrayList = new ArrayList<>();
            RatesArrayList = new ArrayList<>();
            KeysArrayList = new ArrayList<>();
            NoDislikesArrayList = new ArrayList<>();
            NoLikesArrayList = new ArrayList<>();
            NoRepliesArrayList = new ArrayList<>();

            databaseReferenceComments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    CommentsArrayList = new ArrayList<>();
                    NamesArrayList = new ArrayList<>();
                    RatesArrayList = new ArrayList<>();
                    KeysArrayList = new ArrayList<>();
                    NoDislikesArrayList = new ArrayList<>();
                    NoLikesArrayList = new ArrayList<>();
                    NoRepliesArrayList = new ArrayList<>();

                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                        commentSection = artistSnapshot.getValue(CommentSection.class);

                        if (commentSection.getCommentID().equals(Key)) {
                             CommentsArrayList.add(commentSection.getComment());
                             NamesArrayList.add(commentSection.getUserName());
                             RatesArrayList.add(commentSection.getCommentRate());
                             KeysArrayList.add(artistSnapshot.getKey());
                             NoLikesArrayList.add(Integer.parseInt(commentSection.getNoCommentLikes()));
                             NoDislikesArrayList.add(Integer.parseInt(commentSection.getNoCommentDislikes()));
                             //NoRepliesArrayList.add(Integer.parseInt(commentSection.getNoReplies()));
                        }
                    }
                    CommentsView = (ListView) findViewById(R.id.commentsListView);
                    CustomAdapter customAdapter1 = new CustomAdapter();
                    CommentsView.setAdapter(customAdapter1);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        CommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                commentSection = new CommentSection();

                String NewComment = EditComment.getText().toString().trim();

                if (!NewComment.equals("")) {

                    commentSection.setComment(NewComment);
                    commentSection.setUserName(Email);
                    commentSection.setCommentID(Key);
                    databaseReferenceComments.push().setValue(commentSection);
                    CommentsArrayList.add(NewComment);
                    NamesArrayList.add(Email);
                    RatesArrayList.add("None");
                    NoLikesArrayList.add(0);
                    NoDislikesArrayList.add(0);

                    EditComment.setText("");

                    CommentsView = (ListView) findViewById(R.id.commentsListView);
                    CustomAdapter customAdapter1 = new CustomAdapter();
                    CommentsView.setAdapter(customAdapter1);

                }
            }
        });
    }

    @Override
    public void onBackPressed(){

        if(Keys.size() == 1) {
            Intent intent = new Intent(CommentsActivity.this, ReadArticleActivity.class);
            intent.putExtra("Email", Email);
            intent.putExtra("Heading", CommentsTracker.get(0));
            startActivity(intent);
        }

        else{
            Keys.remove(Keys.size()-1);
            CommentsTracker.remove(CommentsTracker.size()-1);
            Intent intent = new Intent(CommentsActivity.this, CommentsActivity.class);
            intent.putExtra("Email", Email);
            intent.putExtra("Key", Keys.get(Keys.size() - 1));
            intent.putExtra("CommentsTitle", CommentsTracker.get(CommentsTracker.size()-1));
            intent.putExtra("Keys", Keys);
            intent.putExtra("CommentsTracker", CommentsTracker);
            startActivity(intent);
        }
    }

    private class CustomAdapter extends BaseAdapter {

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
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View convertView1 = layoutInflater.inflate(R.layout.commentlayout, parent, false);

            TextView textView_NumLikes = convertView1.findViewById(R.id.likeNum);
            TextView textView_NumDislikes = convertView1.findViewById(R.id.dislikeNum);
            //TextView textView_NumComments = convertView1.findViewById(R.id.commentNum);
            ImageView thumbsupImg= convertView1.findViewById(R.id.likeCommentbtn);
            ImageView thumbsdownImg= convertView1.findViewById(R.id.dislikeCommentbtn);
            ImageView commentsImage= convertView1.findViewById(R.id.commentCommentIconBtn);
            TextView textView_heading = convertView1.findViewById(R.id.CommentUser);
            TextView textView_author = convertView1.findViewById(R.id.commentID);

            textView_heading.setText(NamesArrayList.get(position));
            textView_author.setText(CommentsArrayList.get(position));

            textView_NumLikes.setText(String.valueOf(NoLikesArrayList.get(position)));
            textView_NumDislikes.setText(String.valueOf(NoDislikesArrayList.get(position)));

            if(RatesArrayList.get(position).equals("Like")){
                thumbsupImg.setImageResource(R.drawable.like);
                thumbsdownImg.setImageResource(R.drawable.dislikebw);
            }

            else if(RatesArrayList.get(position).equals("Dislike")){
                thumbsupImg.setImageResource(R.drawable.likebw);
                thumbsdownImg.setImageResource(R.drawable.dislike);
            }

            else{
                thumbsupImg.setImageResource(R.drawable.likebw);
                thumbsdownImg.setImageResource(R.drawable.dislikebw);
            }

            commentsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        Keys.add(KeysArrayList.get(position));
                        CommentsTracker.add( CommentsArrayList.get(position));

                        Intent intent = new Intent(CommentsActivity.this, CommentsActivity.class);
                        intent.putExtra("Email", Email);
                        intent.putExtra("Key", KeysArrayList.get(position));
                        intent.putExtra("CommentsTitle", CommentsArrayList.get(position));
                        intent.putExtra("Keys", Keys);
                        intent.putExtra("CommentsTracker", CommentsTracker);
                        startActivity(intent);

                }
            });

            thumbsupImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Rating = "";
                    int NoLikes = NoLikesArrayList.get(position);
                    int NoDislikes = NoDislikesArrayList.get(position);

                    if(RatesArrayList.get(position).equals("Like")){
                        NoLikes-=1;
                        Rating = "None";
                    }

                    else if(RatesArrayList.get(position).equals("Dislike")){
                        NoDislikes -= 1;
                        NoLikes+=1;
                        Rating = "Like";
                    }

                    else if(RatesArrayList.get(position).equals("None")){
                        NoLikes+=1;
                        Rating = "Like";
                    }

                    DatabaseReference databaseReference8;
                    databaseReference8 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeysArrayList.get(position));
                    databaseReference8.child("commentRate").setValue(Rating);
                    databaseReference8.child("noCommentLikes").setValue(String.valueOf(NoLikes));
                    databaseReference8.child("noCommentDislikes").setValue(String.valueOf(NoDislikes));

                }
            });

            thumbsdownImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Rating = "";
                    int NoLikes = NoLikesArrayList.get(position);
                    int NoDislikes = NoDislikesArrayList.get(position);

                    if(RatesArrayList.get(position).equals("Dislike")){
                        NoDislikes-=1;
                        Rating = "None";
                    }

                    else if(RatesArrayList.get(position).equals("Like")){
                        NoDislikes += 1;
                        NoLikes -=1;
                        Rating = "Dislike";
                    }

                    else if(RatesArrayList.get(position).equals("None")){
                        NoDislikes+=1;
                        Rating = "Dislike";
                    }

                    DatabaseReference databaseReference7;
                    databaseReference7 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeysArrayList.get(position));
                    databaseReference7.child("commentRate").setValue(Rating);
                    databaseReference7.child("noCommentDislikes").setValue(String.valueOf(NoDislikes));
                    databaseReference7.child("noCommentLikes").setValue(String.valueOf(NoLikes));
                }
            });
            return convertView1;
        }
    }
}