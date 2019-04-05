package com.example.wits_vuvuzela_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ReadArticleActivity extends AppCompatActivity {

    TextView ArticleBody;
    TextView ArticleHeading;
    String urlLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);

        Bundle bundle = getIntent().getExtras();

        String heading = bundle.getString("Heading");
        String link = bundle.getString("Link");

        urlLink = link;

        ArticleHeading = (TextView) findViewById(R.id.ReadArticleHeading);
        ArticleBody = (TextView) findViewById(R.id.ReadArticleBody);
        ArticleBody.setMovementMethod(new ScrollingMovementMethod());

        ArticleHeading.setText(heading);
        ArticleBody.setText("Article Loading , Please Wait ...");


    }


}
