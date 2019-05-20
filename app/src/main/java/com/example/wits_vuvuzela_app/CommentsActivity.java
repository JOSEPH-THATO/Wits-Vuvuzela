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
import android.widget.Button;
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
import java.util.Collections;

public class CommentsActivity extends AppCompatActivity {

    EditText EditComment;
    ImageView CommentButton;
    Button btnSortByLike;
    Button btnSortByRcnt;
    private ListView CommentsView;
    DatabaseReference databaseReferenceComments;
    ArrayList<String> KeysArrayList;
    ArrayList<String> KeysNumReplies;
    ArrayList<CommentSection> CommentsList;
    String Key = "";
    String Email = "";
    TextView Article1;
    TextView CommentTitle1;
    CommentSection commentSection;
    int NumReplies = 0;
    String CommentType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("Email");
        String key = bundle.getString("Key");
        String commentTitle = bundle.getString("CommentsTitle");
        String commentType = bundle.getString("CommentType");
        int NumberReplies = bundle.getInt("NumberReplies");

        databaseReferenceComments = FirebaseDatabase.getInstance().getReference().child("CommentSection");

        Key = key;
        Email = email;
        NumReplies = NumberReplies;
        CommentType = commentType;

        EditComment = (EditText) findViewById(R.id.editComments);
        CommentButton = (ImageView) findViewById(R.id.commentBtns);
        Article1 = (TextView) findViewById(R.id.title);
        CommentTitle1 = (TextView) findViewById(R.id.CommentTitle);
        btnSortByLike = (Button) findViewById(R.id.sortlikeBtn);
        btnSortByRcnt = (Button) findViewById(R.id.sortrecentBtn);

        CommentTitle1.setText(commentTitle);

        if (Key.equals("")) {
            Toast.makeText(CommentsActivity.this, "No Key Found ", Toast.LENGTH_LONG).show();
        } else {

            databaseReferenceComments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    CommentsList = new ArrayList<>();
                    KeysNumReplies = new ArrayList<>();
                    KeysArrayList = new ArrayList<>();

                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                        commentSection = artistSnapshot.getValue(CommentSection.class);

                        if (commentSection.getCommentID().equals(Key)) {

                            CommentsList.add(commentSection);
                            KeysArrayList.add(artistSnapshot.getKey());
                            KeysNumReplies.add(commentSection.getNoReplies());
                        }
                    }
                    btnSortByRcnt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reverseArrayLists();
                            CommentsView = (ListView) findViewById(R.id.commentsListView);
                            CustomAdapter customAdapter1 = new CustomAdapter();
                            CommentsView.setAdapter(customAdapter1);
                        }
                    });
                    CommentsView = (ListView) findViewById(R.id.commentsListView);
                    CustomAdapter customAdapter1 = new CustomAdapter();
                    CommentsView.setAdapter(customAdapter1);

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

                                DatabaseReference databaseReference99;
                                if (CommentType.equals("Article")) {
                                    databaseReference99 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
                                    databaseReference99.child("noArticleReplies").setValue(String.valueOf(NumReplies += 1));
                                } else if (CommentType.equals("Comment")) {

                                databaseReference99 = FirebaseDatabase.getInstance().getReference("CommentSection").child(Key);
                                    databaseReference99.child("noReplies").setValue(String.valueOf(NumReplies += 1));
                                }

                                CommentsList.add(commentSection);

                                EditComment.setText("");

                                CommentsView = (ListView) findViewById(R.id.commentsListView);
                                CustomAdapter customAdapter1 = new CustomAdapter();
                                CommentsView.setAdapter(customAdapter1);
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void reverseArrayLists(){

        Collections.reverse(CommentsList);

    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return CommentsList.size();
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
            ImageView thumbsupImg = convertView1.findViewById(R.id.likeCommentbtn);
            ImageView thumbsdownImg = convertView1.findViewById(R.id.dislikeCommentbtn);
            ImageView commentsImage = convertView1.findViewById(R.id.commentCommentIconBtn);
            TextView textView_heading = convertView1.findViewById(R.id.CommentUser);
            TextView textView_author = convertView1.findViewById(R.id.commentID);

            textView_heading.setText(CommentsList.get(position).getUserName());
            textView_author.setText(CommentsList.get(position).getComment());
            textView_NumLikes.setText(CommentsList.get(position).getNoCommentLikes());
            textView_NumDislikes.setText(CommentsList.get(position).getNoCommentDislikes());
            textView_NumComments.setText(CommentsList.get(position).getNoReplies());

            if (CommentsList.get(position).getCommentLikedList().contains(Email)) {
                thumbsupImg.setImageResource(R.drawable.like);
                thumbsdownImg.setImageResource(R.drawable.dislikebw);
            } else if (CommentsList.get(position).getCommentDislikedList().contains(Email)) {
                thumbsupImg.setImageResource(R.drawable.likebw);
                thumbsdownImg.setImageResource(R.drawable.dislike);
            } else {
                thumbsupImg.setImageResource(R.drawable.likebw);
                thumbsdownImg.setImageResource(R.drawable.dislikebw);
            }

            commentsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(CommentsActivity.this, CommentsActivity.class);
                    intent.putExtra("Email", Email);
                    intent.putExtra("Key", KeysArrayList.get(position));
                    intent.putExtra("CommentsTitle", CommentsList.get(position).getComment());
                    intent.putExtra("NumberReplies", Integer.parseInt(CommentsList.get(position).getNoReplies()));
                    intent.putExtra("CommentType", "Comment");

                    startActivity(intent);
                }
            });

            thumbsupImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CommentSection commentSectionLike = CommentsList.get(position);

                    commentSectionLike.LikeComment(Email);

                    DatabaseReference databaseReference8;
                    databaseReference8 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeysArrayList.get(position));
                    databaseReference8.child("commentRate").setValue("Like");
                    databaseReference8.child("noCommentLikes").setValue(commentSectionLike.getNoCommentLikes());
                    databaseReference8.child("noCommentDislikes").setValue(commentSectionLike.getNoCommentDislikes());
                    databaseReference8.child("commentLikedList").setValue(commentSectionLike.getCommentLikedList());
                    databaseReference8.child("commentDislikedList").setValue(commentSectionLike.getCommentDislikedList());

                }
            });

            thumbsdownImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CommentSection commentSectionDislike = CommentsList.get(position);

                    commentSectionDislike.DislikeComment(Email);

                    DatabaseReference databaseReference7;
                    databaseReference7 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeysArrayList.get(position));
                    databaseReference7.child("commentRate").setValue("Dislike");
                    databaseReference7.child("noCommentLikes").setValue(commentSectionDislike.getNoCommentLikes());
                    databaseReference7.child("noCommentDislikes").setValue(commentSectionDislike.getNoCommentDislikes());
                    databaseReference7.child("commentLikedList").setValue(commentSectionDislike.getCommentLikedList());
                    databaseReference7.child("commentDislikedList").setValue(commentSectionDislike.getCommentDislikedList());
                }
            });
            return convertView1;
        }
    }
}
