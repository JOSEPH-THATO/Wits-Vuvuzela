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

import com.google.firebase.auth.FirebaseAuth;
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
    ArrayList<String> UserLikedList1;
    ArrayList<String> UserDislikedList1;
    ArrayList<Integer> NoLikesArrayList;
    ArrayList<Integer> NoDislikesArrayList;
    ArrayList<Integer> NoRepliesArrayList;
    String UserLikeList = "";
    String UserDislikedList = "";
    String Key = "huhu";
    String Email = "huhu";
    TextView Article1;
    TextView CommentTitle1;
    CommentSection commentSection;
    ArrayList<String> Keys;
    ArrayList<String> CommentsTracker;
    Integer NumReplies = 0;
    String CommentType = "";

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
        String commentType = bundle.getString("CommentType");
        Integer NumberReplies = bundle.getInt("NumberReplies");
        keys = bundle.getStringArrayList("Keys");
        commentTracker = bundle.getStringArrayList("CommentsTracker");

        Keys = keys;
        Keys = keys;
        CommentsTracker = commentTracker;
        NumReplies = NumberReplies;
        CommentType = commentType;

        databaseReferenceComments = FirebaseDatabase.getInstance().getReference().child("CommentSection");

        CommentsArrayList = new ArrayList<>();
        NamesArrayList = new ArrayList<>();
        KeysArrayList = new ArrayList<>();
        RatesArrayList = new ArrayList<>();
        NoDislikesArrayList = new ArrayList<>();
        NoLikesArrayList = new ArrayList<>();
        NoRepliesArrayList = new ArrayList<>();
        UserDislikedList1 = new ArrayList<>();
        UserLikedList1 = new ArrayList<>();

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
        Article1.setText(Key);

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
            UserDislikedList1 = new ArrayList<>();
            UserLikedList1 = new ArrayList<>();

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
                    UserDislikedList1 = new ArrayList<>();
                    UserLikedList1 = new ArrayList<>();

                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                        commentSection = artistSnapshot.getValue(CommentSection.class);

                        if (commentSection.getCommentID().equals(Key)) {

                             CommentsArrayList.add(commentSection.getComment());
                             NamesArrayList.add(commentSection.getUserName());
                             RatesArrayList.add(commentSection.getCommentRate());
                             KeysArrayList.add(artistSnapshot.getKey());
                             NoLikesArrayList.add(Integer.parseInt(commentSection.getNoCommentLikes()));
                             NoDislikesArrayList.add(Integer.parseInt(commentSection.getNoCommentDislikes()));
                             NoRepliesArrayList.add(Integer.parseInt(commentSection.getNoReplies()));
                             UserLikeList = commentSection.getCommentLikedList();
                             UserDislikedList = commentSection.getCommentDislikedList();
                             UserLikedList1.add(UserLikeList);
                             UserDislikedList1.add(UserDislikedList);
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

                    DatabaseReference databaseReference99;
                    if(CommentType.equals("Article")){
                        databaseReference99 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
                        databaseReference99.child("noArticleReplies").setValue(String.valueOf(NumReplies+=1));
                    }

                    else if(CommentType.equals("Comment")){
                        databaseReference99 = FirebaseDatabase.getInstance().getReference("CommentSection").child(Key);
                        databaseReference99.child("noReplies").setValue(String.valueOf(NumReplies+=1));

                    }

                    RatesArrayList.add("None");
                    NoLikesArrayList.add(0);
                    NoDislikesArrayList.add(0);
                    NoRepliesArrayList.add(NumReplies);
                    UserDislikedList1.add("User1/User2");
                    UserLikedList1.add("User1/User2");

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
            intent.putExtra("CommentType", "Comment" );
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
            TextView textView_NumComments = convertView1.findViewById(R.id.commentNum);
            ImageView thumbsupImg= convertView1.findViewById(R.id.likeCommentbtn);
            ImageView thumbsdownImg= convertView1.findViewById(R.id.dislikeCommentbtn);
            ImageView commentsImage= convertView1.findViewById(R.id.commentCommentIconBtn);
            TextView textView_heading = convertView1.findViewById(R.id.CommentUser);
            TextView textView_author = convertView1.findViewById(R.id.commentID);

            textView_heading.setText(NamesArrayList.get(position));
            textView_author.setText(CommentsArrayList.get(position));

            textView_NumLikes.setText(String.valueOf(NoLikesArrayList.get(position)));
            textView_NumDislikes.setText(String.valueOf(NoDislikesArrayList.get(position)));
            textView_NumComments.setText(String.valueOf(NoRepliesArrayList.get(position)));

            if(UserLikedList1.get(position).contains(Email)){
                thumbsupImg.setImageResource(R.drawable.like);
                thumbsdownImg.setImageResource(R.drawable.dislikebw);
            }

            else if(UserDislikedList1.get(position).contains(Email)){
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
                        intent.putExtra("NumberReplies", NoRepliesArrayList.get(position) );
                        intent.putExtra("CommentType", "Comment" );

                    startActivity(intent);

                }
            });

            thumbsupImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Rating = "";
                    int NoLikes = NoLikesArrayList.get(position);
                    int NoDislikes = NoDislikesArrayList.get(position);

                    if(UserLikedList1.get(position).contains(Email)){
                        NoLikes-=1;
                        Rating = "None";
                        UserLikeList = RemoveUserFromLikedCommentList(Email,UserLikeList);
                    }

                    else if(UserDislikedList1.get(position).contains(Email)){
                        NoDislikes -= 1;
                        NoLikes+=1;
                        Rating = "Like";
                        UserDislikedList = RemoveUserFromDislikedCommentList(Email,UserDislikedList);
                        UserLikeList = AddUserToLikedList(Email,UserLikeList);
                    }

                    else{
                        NoLikes+=1;
                        Rating = "Like";
                        UserLikeList = AddUserToLikedList(Email,UserLikeList);
                    }

                    DatabaseReference databaseReference8;
                    databaseReference8 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeysArrayList.get(position));
                    databaseReference8.child("commentRate").setValue(Rating);
                    databaseReference8.child("noCommentLikes").setValue(String.valueOf(NoLikes));
                    databaseReference8.child("noCommentDislikes").setValue(String.valueOf(NoDislikes));
                    databaseReference8.child("commentLikedList").setValue(String.valueOf(UserLikeList));
                    databaseReference8.child("commentDislikedList").setValue(String.valueOf(UserDislikedList));

                }
            });

            thumbsdownImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Rating = "";
                    int NoLikes = NoLikesArrayList.get(position);
                    int NoDislikes = NoDislikesArrayList.get(position);

                    if(UserDislikedList.contains(Email)){
                        NoDislikes-=1;
                        Rating = "None";
                        UserDislikedList = RemoveUserFromDislikedCommentList(Email,UserDislikedList);
                    }

                    else if(UserLikeList.contains(Email)){
                        NoDislikes += 1;
                        NoLikes -=1;
                        Rating = "Dislike";
                        UserDislikedList = AddUserToDislikedList(Email,UserDislikedList);
                        UserLikeList = RemoveUserFromLikedCommentList(Email,UserLikeList);
                    }

                    else{
                        NoDislikes+=1;
                        Rating = "Dislike";
                        UserDislikedList = AddUserToDislikedList(Email,UserDislikedList);
                    }

                    DatabaseReference databaseReference7;
                    databaseReference7 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeysArrayList.get(position));
                    databaseReference7.child("commentRate").setValue(Rating);
                    databaseReference7.child("noCommentDislikes").setValue(String.valueOf(NoDislikes));
                    databaseReference7.child("noCommentLikes").setValue(String.valueOf(NoLikes));
                    databaseReference7.child("commentLikedList").setValue(UserLikeList);
                    databaseReference7.child("commentDislikedList").setValue(UserDislikedList);

                }
            });
            return convertView1;
        }
    }

    public String AddUserToLikedList(String User,String LikedList) {

        if(LikedList.equals("")){
            LikedList+=User;
        }
        else {
            LikedList += ( "/" + User);
        }

        return LikedList;
    }

    public String AddUserToDislikedList(String User,String DislikedList) {
        if (DislikedList.equals("")) {
            DislikedList += User;
        }
        else {
            DislikedList += ( "/" + User);
        }
        return DislikedList;
    }

    public String RemoveUserFromLikedCommentList(String User,String LikedList) {

        String[] LikedArticle = LikedList.split("/");

        String NewList = "User";

        for (int i = 0; i < LikedArticle.length; ++i) {
            if (User.equals(LikedArticle[i])) {
                continue;
            }

            NewList += ("/" + LikedArticle[i]);
        }

        return NewList;
    }

    public String RemoveUserFromDislikedCommentList(String User,String DislikedList) {

        String[] DislikedComment = DislikedList.split("/");

        String NewList = "User1";

        for (int i = 0; i < DislikedComment.length; ++i) {
            if (User.equals(DislikedComment[i])) {
                continue;
            }

            NewList += ("/" + DislikedComment[i]);
        }

        return NewList;
    }

}
/*
 if(RatesArrayList.get(position).equals("Like")){
         NoLikes-=1;
         Rating = "None";
         RemoveUserFromLikedCommentList(Email,UserLikeList);
         }

         else if(RatesArrayList.get(position).equals("Dislike")){
         NoDislikes -= 1;
         NoLikes+=1;
         Rating = "Like";
         RemoveUserFromDislikedCommentList(Email,UserDislikedList);
         AddUserToLikedList(Email,UserLikeList);
         }

         else if(RatesArrayList.get(position).equals("None")){
         NoLikes+=1;
         Rating = "Like";
         AddUserToLikedList(Email,UserLikeList);
         }
*/