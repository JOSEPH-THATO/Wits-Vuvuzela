package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

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
    ArrayList<String> CommentIndent;
    ArrayList<CommentSection> CommentsList;
    ArrayList<String> FullKeysArrayList;
    ArrayList<String> FullKeysNumReplies;
    ArrayList<String> FullCommentIndent;
    ArrayList<CommentSection> FullCommentsList;
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

        Intent bundle = getIntent();
        String email = bundle.getStringExtra("Email");
        String key = bundle.getStringExtra("Key");
        String commentTitle = bundle.getStringExtra("CommentsTitle");
        String commentType = bundle.getStringExtra("CommentType");
        int NumberReplies = bundle.getIntExtra("Number of Replies", NumReplies);
        String token = bundle.getStringExtra("Token");

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
        BackArrow = (ImageButton) findViewById(R.id.backArrow);
        CommentTitle1.setText(commentTitle);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CommentsActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_by));
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
                    CommentIndent = new ArrayList<>();

                    FullCommentsList = new ArrayList<>();
                    FullKeysNumReplies = new ArrayList<>();
                    FullKeysArrayList = new ArrayList<>();
                    FullCommentIndent = new ArrayList<>();

                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                        commentSection = artistSnapshot.getValue(CommentSection.class);

                        FullCommentsList.add(commentSection);
                        FullKeysArrayList.add(artistSnapshot.getKey());
                        FullKeysNumReplies.add(commentSection.getNoReplies());
                        FullCommentIndent.add(commentSection.getCommentRate());
                    }


                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                        commentSection = artistSnapshot.getValue(CommentSection.class);

                        if (commentSection.getCommentID().equals(Key)) {

                            String CommentKey = artistSnapshot.getKey();

                            CommentsList.add(commentSection);
                            KeysArrayList.add(artistSnapshot.getKey());
                            KeysNumReplies.add(commentSection.getNoReplies());
                            CommentIndent.add(commentSection.getCommentRate());

                            Hierarchialdata(CommentKey);

                            //String commentKey = commentSection.getCommentID();
                            //retrieveReplies(commentKey, dataSnapshot.getChildren());
                        }
                    }
                    /**
                     btnSortByLike.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                    sortByLikes();
                    CommentsView = (ListView) findViewById(R.id.commentsListView);
                    CustomAdapter customAdapter1 = new CustomAdapter();
                    CommentsView.setAdapter(customAdapter1);
                    }
                    });

                     btnSortByRcnt.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
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
                            listItems = 1;
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
                                commentSection.setCommentRate("0");
                                databaseReferenceComments.push().setValue(commentSection);

                                Toast.makeText(CommentsActivity.this, "Your comment has been added", Toast.LENGTH_SHORT).show();


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

    private void retrieveReplies(String commentKey, Iterable<DataSnapshot> children) {
        for (DataSnapshot artistSnapshot : children) {
            CommentSection commentSection = artistSnapshot.getValue(CommentSection.class);
            if (commentSection != null && commentSection.getCommentID().equals(commentKey)) {
                CommentsList.add(commentSection);
                KeysArrayList.add(artistSnapshot.getKey());
                KeysNumReplies.add(commentSection.getNoReplies());
                retrieveReplies(commentSection.getCommentID(), children);
            }
        }
    }

  /*public void sortByLikes() {
>>>>>>> 1bcaa90be40fbe43027199467808a6bd1c772df5

        Collections.sort(CommentsList, new Comparator<CommentSection>(){
            public int compare(CommentSection s1, CommentSection s2) {
                return s1.getNoCommentLikes().compareToIgnoreCase(s2.getNoCommentLikes());
            }
        });
        Collections.reverse(CommentsList);

    }*/

    public void reverseArrayLists() {
        Collections.reverse(CommentsList);
    }

    public void Hierarchialdata(String CommentKey) {


        for (int k = 0; k < FullCommentsList.size(); ++k) {

            CommentSection commentSection2 = FullCommentsList.get(k);

            if (commentSection2.getCommentID().equals(CommentKey)) {
                CommentsList.add(commentSection2);
                KeysArrayList.add(FullKeysArrayList.get(k));
                //  CommentIndent.add(FullCommentIndent.get(k));
                Hierarchialdata(FullKeysArrayList.get(k));

            }
        }
    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return CommentsList.size();
            // return listItems;
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

            View convertView1 = layoutInflater.inflate(R.layout.reply_layout, parent, false);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            // int indent = Integer.parseInt(CommentsList.get(position).getCommentRate());

            params.setMarginStart(150);

            parent.setLayoutParams(params);

            TextView textView_NumLikes = convertView1.findViewById(R.id.likeNum);
            TextView textView_NumDislikes = convertView1.findViewById(R.id.dislikeNum);
            TextView textView_NumComments = convertView1.findViewById(R.id.commentNum);
            ImageView thumbsupImg = convertView1.findViewById(R.id.likeCommentbtn);
            ImageView thumbsdownImg = convertView1.findViewById(R.id.dislikeCommentbtn);
            ImageView commentsImage = convertView1.findViewById(R.id.commentCommentIconBtn);
            TextView textView_heading = convertView1.findViewById(R.id.CommentUser);
            TextView textView_author = convertView1.findViewById(R.id.commentID);
            final EditText editText_comment = convertView1.findViewById(R.id.editCommentsl);
            ImageView button_comment = convertView1.findViewById(R.id.commentBtnsl);
            final LinearLayout linearLayout = convertView1.findViewById(R.id.CommentLayout);

            linearLayout.setVisibility(View.GONE);

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

            button_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    commentSection = new CommentSection();

                    String NewComment = editText_comment.getText().toString().trim();

                    if (!NewComment.equals("")) {

                        commentSection.setComment(NewComment);
                        commentSection.setUserName(Email);
                        commentSection.setCommentID(KeysArrayList.get(position));
                        commentSection.setCommentToken(Token);
                        //newindent+=10;
                        int indent = Integer.parseInt(CommentsList.get(position).getCommentRate());

                        indent += 100;
                        commentSection.setCommentRate(String.valueOf(indent));
                        databaseReferenceComments.push().setValue(commentSection);

                        DatabaseReference databaseReference99;

                        databaseReference99 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeysArrayList.get(position));
                        // databaseReference99.child("noReplies").setValue(String.valueOf(NumReplies += 1));
                        databaseReference99.child("userComment").setValue(Email);

                        CommentsList.add(commentSection);

                        linearLayout.setVisibility(View.GONE);

                        CommentsView = (ListView) findViewById(R.id.commentsListView);
                        CustomAdapter customAdapter1 = new CustomAdapter();
                        CommentsView.setAdapter(customAdapter1);

                    }
                }
            });

            commentsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            });

            thumbsupImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CommentSection commentSectionLike = CommentsList.get(position);

                    //    Toast.makeText(CommentsActivity.this,"You have liked this comment",Toast.LENGTH_SHORT).show();

                    commentSectionLike.LikeComment(Email);

                    DatabaseReference databaseReference8;
                    databaseReference8 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeysArrayList.get(position));
                    databaseReference8.child("commentRate").setValue("Like");
                    databaseReference8.child("noCommentLikes").setValue(commentSectionLike.getNoCommentLikes());
                    databaseReference8.child("noCommentDislikes").setValue(commentSectionLike.getNoCommentDislikes());
                    databaseReference8.child("commentLikedList").setValue(commentSectionLike.getCommentLikedList());
                    databaseReference8.child("commentDislikedList").setValue(commentSectionLike.getCommentDislikedList());
                    databaseReference8.child("userDislike").setValue(commentSectionLike.getUserDislike());
                    // databaseReference8.child("commentToken").setValue(Token);
                    databaseReference8.child("userLike").setValue(commentSectionLike.getUserLike());

                    notification = new Notification();

                    notification.setNotificationFrom(Email);
                    notification.setNotificationTo(commentSectionLike.getUserName());
                    notification.setNotificationBody(commentSectionLike.getComment());
                    notification.setNotificationTitle("Liked Your Comment");
                    databaseReferenceNotifications.push().setValue(notification);
                }
            });

            thumbsdownImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(CommentsActivity.this,"You have disliked this comment",Toast.LENGTH_SHORT).show();

                    CommentSection commentSectionDislike = CommentsList.get(position);

                    commentSectionDislike.DislikeComment(Email);

                    DatabaseReference databaseReference7;
                    databaseReference7 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeysArrayList.get(position));
                    databaseReference7.child("commentRate").setValue("Dislike");
                    databaseReference7.child("noCommentLikes").setValue(commentSectionDislike.getNoCommentLikes());
                    databaseReference7.child("noCommentDislikes").setValue(commentSectionDislike.getNoCommentDislikes());
                    databaseReference7.child("commentLikedList").setValue(commentSectionDislike.getCommentLikedList());
                    databaseReference7.child("commentDislikedList").setValue(commentSectionDislike.getCommentDislikedList());
                    databaseReference7.child("userDislike").setValue(commentSectionDislike.getUserDislike());
                    databaseReference7.child("userLike").setValue(commentSectionDislike.getUserLike());
                    //   databaseReference7.child("commentToken").setValue(Token);
                    databaseReference7.child("userDislike").setValue(commentSectionDislike.getUserDislike());

                    notification = new Notification();

                    notification.setNotificationFrom(Email);
                    notification.setNotificationTo(commentSectionDislike.getUserName());
                    notification.setNotificationBody(commentSectionDislike.getComment());
                    notification.setNotificationTitle("Disliked Your Comment");
                    databaseReferenceNotifications.push().setValue(notification);
                }
            });
            return convertView1;
        }
    }
}
