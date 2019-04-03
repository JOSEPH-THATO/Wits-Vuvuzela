package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadArticleActivity extends AppCompatActivity {

    TextView ArticleHeading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);

        Bundle bundle = getIntent().getExtras();

        String heading  = bundle.getString("Author");

        ArticleHeading = (TextView)findViewById(R.id.ArticleAuthor);

        ArticleHeading.setText(heading);


    }
}
