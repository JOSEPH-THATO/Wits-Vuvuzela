package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CommentsActivity extends AppCompatActivity {

    EditText EditComment;
    ImageView CommentButton;
    ImageButton BackArrow;
    private ListView CommentsView;
    DatabaseReference databaseReferenceComments;
    DatabaseReference databaseReferenceNotifications;
    ArrayList<String> KeysArrayList;
    ArrayList<String> KeysNumReplies;
    ArrayList<CommentSection> CommentsList;
    String Key = "";
    String Email = "";
    String Token = "";
    TextView Article1;
    TextView CommentTitle1;
    CommentSection commentSection;
    Notification notification;
    int NumReplies = 0;
    int listItems = 1;
    String CommentType = "";

    Button btnShowMore;
    Button btnShowLess;
    CustomAdapter customAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

<<<<<<< HEAD
        Intent bundle = getIntent();
        String email = bundle.getStringExtra("Email");
        String key = bundle.getStringExtra("Key");
        String commentTitle = bundle.getStringExtra("CommentsTitle");
        String commentType = bundle.getStringExtra("CommentType");
        int NumberReplies = bundle.getIntExtra("Number of Replies", NumReplies);
=======
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("Email");
        String key = bundle.getString("Key");
        String commentTitle = bundle.getString("CommentsTitle");
        String commentType = bundle.getString("CommentType");
        String token = bundle.getString("Token");

        int NumberReplies = bundle.getInt("NumberReplies");
>>>>>>> a828b3eef5196a6cf02df338700595d3ea8f2508

        databaseReferenceComments = FirebaseDatabase.getInstance().getReference().child("CommentSection");
        databaseReferenceNotifications = FirebaseDatabase.getInstance().getReference().child("Notification");

        Key = key;
        Email = email;
        Token = token;
        NumReplies = NumberReplies;
        CommentType = commentType;

        EditComment = (EditText) findViewById(R.id.editComments);
        CommentButton = (ImageView) findViewById(R.id.commentBtns);
        Article1 = (TextView) findViewById(R.id.title);
        CommentTitle1 = (TextView) findViewById(R.id.CommentTitle);
        BackArrow = (ImageButton)findViewById(R.id.backArrow);
        CommentTitle1.setText(commentTitle);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CommentsActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.sort_by));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);


        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        /*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ItemSelected = parent.getItemAtPosition(position).toString();
/*
                if (ItemSelected.equals("Sort by Like")){
 //                   sortByLikes();
                    CommentsView = (ListView) findViewById(R.id.commentsListView);
                    CustomAdapter customAdapter1 = new CustomAdapter();
//                    CommentsView.setAdapter(customAdapter1);
                }
                else if(ItemSelected.equals("Sort by recent")){

                    reverseArrayLists();
                    CommentsView = (ListView) findViewById(R.id.commentsListView);
                    CustomAdapter customAdapter1 = new CustomAdapter();
                    CommentsView.setAdapter(customAdapter1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        if (key == "") {
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

                            /*for (DataSnapshot artistSnapshots : dataSnapshot.getChildren()) {

                               CommentSection commentSection1 = artistSnapshots.getValue(CommentSection.class);

                                if (commentSection1.getCommentID().equals(artistSnapshot.getKey())) {

                                    CommentsList.add(commentSection1);
                                    KeysNumReplies.add(commentSection1.getNoReplies());

                                }
                            }*/

                            CommentsList.add(commentSection);
                            KeysArrayList.add(artistSnapshot.getKey());
                            KeysNumReplies.add(commentSection.getNoReplies());
                        }
                    }
                    /**
                    btnSortByLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sortByLikes();
                            CommentsView = (ListView) findViewById(R.id.commentsListView);
                            CustomAdapter customAdapter1 = new CustomAdapter();
                            CommentsView.setAdapter(customAdapter1);
                        }
                    });

                    btnSortByRcnt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reverseArrayLists();
                            CommentsView = (ListView) findViewById(R.id.commentsListView);
                            CustomAdapter customAdapter1 = new CustomAdapter();
                            CommentsView.setAdapter(customAdapter1);

                        }
                    });
**/
                    CommentsView = (ListView) findViewById(R.id.commentsListView);

                    btnShowMore = new Button(CommentsActivity.this);
                    btnShowMore.setText("show more");
                    btnShowLess = new Button(CommentsActivity.this);
                    btnShowLess.setText("show less");

                    CommentsView.addFooterView(btnShowMore);
                    CommentsView.addFooterView(btnShowLess);

                    btnShowMore.setVisibility(View.VISIBLE);
                    btnShowLess.setVisibility(View.GONE);

                    customAdapter1 = new CustomAdapter();
                    CommentsView.setAdapter(customAdapter1);


                     btnShowMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                listItems += CommentsList.size() - listItems;
                                customAdapter1.notifyDataSetChanged();
                                btnShowMore.setVisibility(View.GONE);
                                btnShowLess.setVisibility(View.VISIBLE);
                            }
                     });


                    btnShowLess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listItems =1;
                            customAdapter1.notifyDataSetChanged();
                            btnShowMore.setVisibility(View.VISIBLE);
                            btnShowLess.setVisibility(View.GONE);
                        }
                    });



                    CommentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            commentSection = new CommentSection();

                            String NewComment = EditComment.getText().toString().trim();

                            if (!NewComment.equals("")) {

                                commentSection.setComment(NewComment);
                                commentSection.setUserName(Email);
                                commentSection.setCommentID(Key);
                                commentSection.setCommentToken(Token);
                                databaseReferenceComments.push().setValue(commentSection);

                                DatabaseReference databaseReference99;
                                if (CommentType.equals("Article")) {
                                    databaseReference99 = FirebaseDatabase.getInstance().getReference("Article").child(Key);
                                    databaseReference99.child("noArticleReplies").setValue(String.valueOf(NumReplies += 1));

                                } else if (CommentType.equals("Comment")) {

                                databaseReference99 = FirebaseDatabase.getInstance().getReference("CommentSection").child(Key);
                                    databaseReference99.child("noReplies").setValue(String.valueOf(NumReplies += 1));
                                    databaseReference99.child("userComment").setValue(Email);

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

  /*public void sortByLikes() {

        Collections.sort(CommentsList, new Comparator<CommentSection>(){
            public int compare(CommentSection s1, CommentSection s2) {
                return s1.getNoCommentLikes().compareToIgnoreCase(s2.getNoCommentLikes());
            }
        });
        Collections.reverse(CommentsList);

    }*/
    public void reverseArrayLists(){

        Collections.reverse(CommentsList);

    }

    public void Hierarchialdata() {


    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
<<<<<<< HEAD
           return 0;
=======

            return listItems;
>>>>>>> a828b3eef5196a6cf02df338700595d3ea8f2508
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
                   // databaseReference8.child("commentToken").setValue(Token);
                    databaseReference8.child("userLike").setValue(Email);

                    notification = new Notification();

                    notification.setNotificationFrom(Email);
                    notification.setNotificationTo(commentSectionLike.getUserName());
                    notification.setNotificationBody(commentSectionLike.getComment());
                    notification.setNotificationTitle("Liked Your Comment");
                    //notification.setNotificationToken(commentSectionLike.getCommentToken());
                    databaseReferenceNotifications.push().setValue(notification);



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
                 //   databaseReference7.child("commentToken").setValue(Token);
                    databaseReference7.child("userDislike").setValue(Email);

                    notification = new Notification();

                    notification.setNotificationFrom(Email);
                    notification.setNotificationTo(commentSectionDislike.getUserName());
                    notification.setNotificationBody(commentSectionDislike.getComment());
                    notification.setNotificationTitle("Disliked Your Comment");
                 //   notification.setNotificationToken(commentSectionDislike.getCommentToken());
                    databaseReferenceNotifications.push().setValue(notification);


                }
            });
            return convertView1;
        }
    }

}
