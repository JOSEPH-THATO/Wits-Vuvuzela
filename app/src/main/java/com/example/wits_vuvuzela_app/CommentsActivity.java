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
    FirebaseAuth firebaseauth;
    ImageView CommentButton;
    private ListView CommentsView;
    DatabaseReference databaseReferenceComments;
    DatabaseReference databaseReferenceKey;
    DatabaseReference databaseReferenceRate1;
    DatabaseReference databaseReferenceRate2;
    ArrayList<String> CommentsArrayList;
    ArrayList<String> RatesArrayList;
    ArrayList<String> NamesArrayList;
    ArrayList<Integer> KeysArrayList;
    String Key = "huhu";
    String Email = "huhu";
    TextView Article1;
    TextView CommentTitle1;
    String KeyKey = "";
    CommentSection commentSection;
    CommentSection commentSection1;
    CommentSection commentSection2;
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
        databaseReferenceRate2 = FirebaseDatabase.getInstance().getReference().child("CommentSection");

        databaseReferenceRate1 = FirebaseDatabase.getInstance().getReference().child("CommentSection");


        CommentsArrayList = new ArrayList<>();
        NamesArrayList = new ArrayList<>();
        KeysArrayList = new ArrayList<>();
        RatesArrayList = new ArrayList<>();

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

            databaseReferenceComments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    CommentsArrayList = new ArrayList<>();
                    NamesArrayList = new ArrayList<>();
                    RatesArrayList = new ArrayList<>();

                    for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                        commentSection = artistSnapshot.getValue(CommentSection.class);

                        if (commentSection.getCommentID().equals(Key)) {
                            CommentsArrayList.add(commentSection.getComment());
                            NamesArrayList.add(commentSection.getUserName());
                            RatesArrayList.add(commentSection.getCommentRate());
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

            ImageView thumbsupImg= convertView1.findViewById(R.id.likeCommentbtn);
            ImageView thumbsdownImg= convertView1.findViewById(R.id.dislikeCommentbtn);
            ImageView commentsImage= convertView1.findViewById(R.id.commentCommentIconBtn);
            TextView textView_heading = convertView1.findViewById(R.id.CommentUser);
            TextView textView_author = convertView1.findViewById(R.id.commentID);

            textView_heading.setText(NamesArrayList.get(position));
            textView_author.setText(CommentsArrayList.get(position));

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

                    databaseReferenceKey = FirebaseDatabase.getInstance().getReference().child("CommentSection");
                    databaseReferenceKey.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {

                                commentSection1 = artistSnapshot.getValue(CommentSection.class);

                                if (commentSection1.getComment().equals(CommentsArrayList.get(position)) && commentSection1.getUserName().equals(NamesArrayList.get(position)) && commentSection1.getCommentID().equals(Key)) {
                                    KeyKey = artistSnapshot.getKey();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if(!KeyKey.equals("")) {

                        Keys.add(KeyKey);
                        CommentsTracker.add( CommentsArrayList.get(position));

                        Intent intent = new Intent(CommentsActivity.this, CommentsActivity.class);
                        intent.putExtra("Email", Email);
                        intent.putExtra("Key", KeyKey);
                        intent.putExtra("CommentsTitle", CommentsArrayList.get(position));
                        intent.putExtra("Keys", Keys);
                        intent.putExtra("CommentsTracker", CommentsTracker);
                        startActivity(intent);
                    }
                }
            });
            thumbsupImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   databaseReferenceRate2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot  dataSnapshot) {

                            String KeyKey1="";

                            for (DataSnapshot Snapshot1 : dataSnapshot.getChildren()) {

                                CommentSection commentSection3 = Snapshot1.getValue(CommentSection.class);

                                if (commentSection3.getComment().equals(CommentsArrayList.get(position)) && commentSection3.getUserName().equals(NamesArrayList.get(position)) && commentSection3.getCommentID().equals(Key)) {
                                    KeyKey1 = Snapshot1.getKey();
                                }
                            }

                            DatabaseReference databaseReference8;

                            databaseReference8 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeyKey1);
                            databaseReference8.child("commentRate").setValue("Like");
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            thumbsdownImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    databaseReferenceRate1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot  dataSnapshot) {

                            String KeyKey2="";

                            for (DataSnapshot Snapshot3  : dataSnapshot.getChildren()) {

                                commentSection2 = Snapshot3.getValue(CommentSection.class);

                                if (commentSection2.getComment().equals(CommentsArrayList.get(position)) && commentSection2.getUserName().equals(NamesArrayList.get(position)) && commentSection2.getCommentID().equals(Key)) {
                                    KeyKey2 = Snapshot3.getKey();
                                }
                            }

                            DatabaseReference databaseReference7;
                            databaseReference7 = FirebaseDatabase.getInstance().getReference("CommentSection").child(KeyKey2);
                            databaseReference7.child("commentRate").setValue("Dislike");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });
            return convertView1;
        }
    }
}