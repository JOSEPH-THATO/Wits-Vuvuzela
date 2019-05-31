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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class CommentsActivity extends AppCompatActivity {

    EditText EditComment;
    LinearLayout CommentLayoutMain;
    ImageView CommentButton;
    ImageButton BackArrow;
    private ListView CommentsView;
    DatabaseReference databaseReferenceComments;
    DatabaseReference databaseReferenceNotifications;
    ArrayList<String> KeysNumReplies;
    ArrayList<CommentSection> CommentsList;
    ArrayList<CommentSection> CommentsList2;
    ArrayList<CommentSection> FullCommentsList;
    String Key = "";
    String Email = "";
    String Token = "";
    TextView Article1;
    TextView CommentTitle1;
    CommentSection commentSection;
    Notification notification;
    int NumReplies = 0;
    String CommentType = "";
    String CurrentDate = "";
    String sortBy = "Recent";
    String CurrentTime = "";
    Spinner spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent bundle = getIntent();
        String email = bundle.getStringExtra("Email");
        String key = bundle.getStringExtra("Key");
        String commentTitle = bundle.getStringExtra("CommentsTitle");
        String token = bundle.getStringExtra("Token");
        String commentType = bundle.getStringExtra("CommentType");
        int NumberReplies = bundle.getIntExtra("Number of Replies", NumReplies);

        databaseReferenceComments = FirebaseDatabase.getInstance().getReference().child("CommentSection");
        databaseReferenceNotifications = FirebaseDatabase.getInstance().getReference().child("Notification");
        CommentLayoutMain = (LinearLayout) findViewById(R.id.CommentLayoutMain);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time = format.format(calendar.getTime());

        CurrentTime = time;
        CurrentDate = currentDate;
        Key = key;
        Email = email;
        NumReplies = NumberReplies;
        CommentType = commentType;
        Token = token;

        EditComment = (EditText) findViewById(R.id.editComments);
        CommentButton = (ImageView) findViewById(R.id.commentBtns);
        Article1 = (TextView) findViewById(R.id.title);
        CommentTitle1 = (TextView) findViewById(R.id.CommentTitle);
        BackArrow = (ImageButton) findViewById(R.id.backArrow);
        CommentTitle1.setText(commentTitle);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CommentsActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_by));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (key == "") {
            Toast.makeText(CommentsActivity.this, "No Key Found ", Toast.LENGTH_LONG).show();
        } else {

            databaseReferenceComments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    CommentLayoutMain.setVisibility(View.VISIBLE);

                    CommentsList = new ArrayList<>();
                    FullCommentsList = new ArrayList<>();

                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                        commentSection = artistSnapshot.getValue(CommentSection.class);

                        commentSection.setCommentKey(artistSnapshot.getKey());
                        FullCommentsList.add(commentSection);

                        DatabaseReference databaseReference55;
                        databaseReference55 = FirebaseDatabase.getInstance().getReference("CommentSection").child(artistSnapshot.getKey());
                        databaseReference55.child("commentKey").setValue(artistSnapshot.getKey());
                    }

                    RunSpinner();


                    for (DataSnapshot artistSnapshots : dataSnapshot.getChildren()) {

                        CommentSection commentSection1 = artistSnapshots.getValue(CommentSection.class);

                        if (commentSection1.getCommentID().equals(Key)) {

                            CommentsList.add(commentSection1);
                            String CommentKey = commentSection1.getCommentKey();

                            Hierarchialdata(CommentKey);

                        }

                    }
/*
                    if(sortBy.equals("Recent")){
                        Collections.reverse(FullCommentsList);
                        Collections.reverse(CommentsList);

                        CommentsList2 = new ArrayList<>();

                        for (int i = 0;i < CommentsList.size();++i) {

                            CommentSection commentSection = CommentsList.get(i);

                            if (commentSection.getCommentID().equals(Key)) {

                                String CommentKey = commentSection.getCommentKey();

                                CommentsList2.add(commentSection);

                                Hierarchialdata(CommentKey);

                            }
                        }
                        CommentsList = CommentsList2;

                    }

                    else if(sortBy.equals("Likes")){
                        Collections.sort(FullCommentsList, new Comparator<CommentSection>(){
                            public int compare(CommentSection s1, CommentSection s2) {
                                return s1.getNoCommentLikes().compareToIgnoreCase(s2.getNoCommentLikes());
                            }
                        });
                        Collections.reverse(FullCommentsList);

                        Collections.sort(CommentsList, new Comparator<CommentSection>(){
                            public int compare(CommentSection s1, CommentSection s2) {
                                return s1.getNoCommentLikes().compareToIgnoreCase(s2.getNoCommentLikes());
                            }
                        });
                        Collections.reverse(CommentsList);

                        CommentsList2 = new ArrayList<>();

                        for (int i = 0;i < CommentsList.size();++i) {

                            CommentSection commentSection = CommentsList.get(i);

                            if (commentSection.getCommentID().equals(Key)) {

                                String CommentKey = commentSection.getCommentKey();

                                CommentsList2.add(commentSection);
                                KeysNumReplies.add(commentSection.getNoReplies());

                                Hierarchialdata(CommentKey);

                            }
                        }
                        CommentsList = CommentsList2;
                    }
*/
                    CommentsView = (ListView) findViewById(R.id.commentsListView);
                    CustomAdapter customAdapter1 = new CustomAdapter();
                    CommentsView.setAdapter(customAdapter1);

                    CommentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            CommentSection commentSection = new CommentSection();

                            String NewComment = EditComment.getText().toString().trim();

                            if (!NewComment.equals("")) {

                                commentSection.setComment(NewComment);
                                commentSection.setUserName(Email);
                                commentSection.setCommentID(Key);
                                commentSection.setCommentToken(Token);
                                commentSection.setCommentRate("0");
                                commentSection.setCommentTime(CurrentTime + " " + CurrentDate);
                                databaseReferenceComments.push().setValue(commentSection);

                                Toast.makeText(CommentsActivity.this, "Your comment has been added", Toast.LENGTH_SHORT).show();

                                DatabaseReference databaseReference99;

                                if (CommentType.equals("Comment")) {

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

    public void Hierarchialdata(String CommentKey) {

        for (int k = 0; k < FullCommentsList.size(); ++k) {

            CommentSection commentSection2 = FullCommentsList.get(k);

            if (commentSection2.getCommentID().equals(CommentKey)) {
                CommentsList.add(commentSection2);
                Hierarchialdata(FullCommentsList.get(k).getCommentKey());
            }
        }
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

            View convertView1 = layoutInflater.inflate(R.layout.reply_layout, parent, false);

            int indent = Integer.parseInt(CommentsList.get(position).getCommentRate());

            LinearLayout layoutm = convertView1.findViewById(R.id.replylayoutt);
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
            Button showless = convertView1.findViewById(R.id.buttonless);
            Button showmore = convertView1.findViewById(R.id.buttonmore);
            final LinearLayout linearLayout = convertView1.findViewById(R.id.CommentLayout);

            linearLayout.setVisibility(View.GONE);
            showless.setVisibility(View.GONE);
            showmore.setVisibility(View.GONE);

            layoutm.setPadding(indent, 0, 0, 0);
            textView_heading.setText(CommentsList.get(position).getUserName());
            textView_author.setText(CommentsList.get(position).getComment());
            textView_NumLikes.setText(CommentsList.get(position).getNoCommentLikes());
            textView_NumDislikes.setText(CommentsList.get(position).getNoCommentDislikes());
            textView_NumComments.setText(CommentsList.get(position).getNoReplies());

            showless.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentsList.subList(position + 1, CommentsList.size()).clear();
                    CommentsView = (ListView) findViewById(R.id.commentsListView);
                    CustomAdapter customAdapter1 = new CustomAdapter();
                    CommentsView.setAdapter(customAdapter1);
                }
            });
            showmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

               //     for (int i = position; i < CommentsList.size(); ++i) {
               //         CommentsList.add(i, FullCommentsList.get(i));
               //     }

                //    CommentsView = (ListView) findViewById(R.id.commentsListView);
                //    CustomAdapter customAdapter1 = new CustomAdapter();
                //    CommentsView.setAdapter(customAdapter1);
                }
            });

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

                    String NewComment = editText_comment.getText().toString().trim();
                    commentSection.setComment(NewComment);
                    commentSection.setUserName(CommentsList.get(position).getCommentRate());
                    commentSection.setCommentID(CommentsList.get(position).getCommentKey());
                    commentSection.setCommentToken(Token);
                    commentSection.setCommentTime(CurrentDate);
                    int indent = Integer.parseInt(CommentsList.get(position).getCommentRate());
                    indent += 80;
                    commentSection.setCommentRate(String.valueOf(indent));
                    databaseReferenceComments.push().setValue(commentSection);

                    DatabaseReference databaseReference99;
                    databaseReference99 = FirebaseDatabase.getInstance().getReference("CommentSection").child(CommentsList.get(position).getCommentKey());
                    int NoReplies = Integer.parseInt(CommentsList.get(position).getNoReplies());
                    databaseReference99.child("noReplies").setValue(String.valueOf(NoReplies += 1));
                    databaseReference99.child("userComment").setValue(Email);

                }
            });

            thumbsupImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    commentSection = new CommentSection();

                    CommentSection commentSectionLike = CommentsList.get(position);

                    commentSectionLike.LikeComment(Email);

                    DatabaseReference databaseReference8;
                    databaseReference8 = FirebaseDatabase.getInstance().getReference("CommentSection").child(CommentsList.get(position).getCommentKey());
                    databaseReference8.child("noCommentLikes").setValue(commentSectionLike.getNoCommentLikes());
                    databaseReference8.child("noCommentDislikes").setValue(commentSectionLike.getNoCommentDislikes());
                    databaseReference8.child("commentLikedList").setValue(commentSectionLike.getCommentLikedList());
                    databaseReference8.child("commentDislikedList").setValue(commentSectionLike.getCommentDislikedList());
                    databaseReference8.child("userDislike").setValue(commentSectionLike.getUserDislike());
                    databaseReference8.child("userLike").setValue(commentSectionLike.getUserLike());

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
                    databaseReference7 = FirebaseDatabase.getInstance().getReference("CommentSection").child(CommentsList.get(position).getCommentKey());
                    databaseReference7.child("noCommentLikes").setValue(commentSectionDislike.getNoCommentLikes());
                    databaseReference7.child("noCommentDislikes").setValue(commentSectionDislike.getNoCommentDislikes());
                    databaseReference7.child("commentLikedList").setValue(commentSectionDislike.getCommentLikedList());
                    databaseReference7.child("commentDislikedList").setValue(commentSectionDislike.getCommentDislikedList());
                    databaseReference7.child("userDislike").setValue(commentSectionDislike.getUserDislike());
                    databaseReference7.child("userLike").setValue(commentSectionDislike.getUserLike());

                    notification = new Notification();

                    notification.setNotificationFrom(Email);
                    notification.setNotificationTo(commentSectionDislike.getUserName());
                    notification.setNotificationBody(commentSectionDislike.getComment());
                    notification.setNotificationTitle("Disliked Your Comment");
                    databaseReferenceNotifications.push().setValue(notification);

                }
            });

            commentsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setVisibility(View.VISIBLE);
                    CommentLayoutMain.setVisibility(View.GONE);

                }
            });

            return convertView1;
        }
    }

    public void RunSpinner(){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ItemSelected = parent.getItemAtPosition(position).toString();

                if (ItemSelected.equals("Sort by Like")){
                    //sortByLikes();
                    //CommentsView = (ListView) findViewById(R.id.commentsListView);
                    //CustomAdapter customAdapter1 = new CustomAdapter();
                    //CommentsView.setAdapter(customAdapter1);
                    sortBy = "Likes";
                }
                else if(ItemSelected.equals("Sort by recent")){
                   // reverseArrayLists();
                   // CommentsView = (ListView) findViewById(R.id.commentsListView);
                   // CustomAdapter customAdapter1 = new CustomAdapter();
                   // CommentsView.setAdapter(customAdapter1);

                    sortBy = "Recent";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void sortByLikes(){
        Collections.sort(FullCommentsList, new Comparator<CommentSection>(){
            public int compare(CommentSection s1, CommentSection s2) {
                return s1.getNoCommentLikes().compareToIgnoreCase(s2.getNoCommentLikes());
            }
        });
        Collections.reverse(FullCommentsList);

        Collections.sort(CommentsList, new Comparator<CommentSection>(){
            public int compare(CommentSection s1, CommentSection s2) {
                return s1.getNoCommentLikes().compareToIgnoreCase(s2.getNoCommentLikes());
            }
        });
        Collections.reverse(CommentsList);

        CommentsList2 = new ArrayList<>();

        for (int i = 0;i < CommentsList.size();++i) {

            CommentSection commentSection = CommentsList.get(i);

            if (commentSection.getCommentID().equals(Key)) {

                String CommentKey = commentSection.getCommentKey();

                CommentsList2.add(commentSection);
                KeysNumReplies.add(commentSection.getNoReplies());

                Hierarchialdata(CommentKey);

            }
        }
        CommentsList = CommentsList2;

        CommentsView = (ListView) findViewById(R.id.commentsListView);
        CustomAdapter customAdapter1 = new CustomAdapter();
        CommentsView.setAdapter(customAdapter1);
    }

    public void reverseArrayLists() {

        Collections.reverse(FullCommentsList);
        Collections.reverse(CommentsList);

        CommentsList2 = new ArrayList<>();

        for (int i = 0;i < CommentsList.size();++i) {

            CommentSection commentSection = CommentsList.get(i);

            if (commentSection.getCommentID().equals(Key)) {

                String CommentKey = commentSection.getCommentKey();

                CommentsList2.add(commentSection);
                KeysNumReplies.add(commentSection.getNoReplies());

                Hierarchialdata(CommentKey);

            }
        }
        CommentsList = CommentsList2;

        CommentsView = (ListView) findViewById(R.id.commentsListView);
        CustomAdapter customAdapter1 = new CustomAdapter();
        CommentsView.setAdapter(customAdapter1);
    }

}


