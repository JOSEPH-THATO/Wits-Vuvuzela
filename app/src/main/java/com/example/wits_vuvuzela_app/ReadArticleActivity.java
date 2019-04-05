package com.example.wits_vuvuzela_app;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ReadArticleActivity extends AppCompatActivity {

    TextView ArticleBody;
    TextView ArticleHeading;
    TextView ArticleComment;
    TextView UserEmail;
    ListView viewComments;
    String urlLink;

    String[] UserNames = {"Jacob Zuma","Wits University","Elections"};
    String[] UserComments = {"State Capture Report","Best Institution in Africa","Eskom Crisis"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_article);
        Bundle bundle = getIntent().getExtras();

        CustomAdapter customAdapter = new CustomAdapter();


        viewComments = (ListView)findViewById(R.id.viewCommentsID);

        viewComments.setAdapter(customAdapter);

        String heading = bundle.getString("Heading");
        String link = bundle.getString("Link");

        urlLink = link;

        ArticleHeading = (TextView) findViewById(R.id.ReadArticleHeading);
        ArticleBody = (TextView) findViewById(R.id.ReadArticleBody);
        //ArticleComment = (TextView)findViewById(R.id)
        ArticleBody.setMovementMethod(new ScrollingMovementMethod());

        ArticleHeading.setText(heading);
        ArticleBody.setText("Article Loading , Please Wait ...");

        new doit().execute();

    }

    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return UserNames.length;
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
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View convertView2 = layoutInflater.inflate(R.layout.commentlayout,parent,false);
            TextView txtUserName = convertView2.findViewById(R.id.userEmail);
            TextView txtComment = convertView2.findViewById(R.id.TxtComment);

            txtUserName.setText(UserNames[position]);
            txtComment.setText(UserComments[position]);
            return convertView2;
        }
    }
    public class doit extends AsyncTask<Void, Void, Void> {

        ArrayList<String> Heading1;

        String words = "";
        String[] AuthorOrDate;
        String Author = "";

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Heading1 = new ArrayList<>();

                // String imgURL  = "https://www.google.com/images/srpr/logo11w.png";
                // new DownLoadImageTask(iv).execute(imgURL);
                Document mBlogDocument = Jsoup.connect(urlLink).get();
                Elements mElementDataSize = mBlogDocument.select("div[class=entry-content]");
                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < 10; i++) {

                    Elements mElementArticle = mBlogDocument.select("p").eq(i);
                    String mArticleBody = mElementArticle.text();

                   if(i == 0){
                        //AuthorOrDate = mArticleBody.split();
                        Author = mArticleBody;
                    }

                    else if(i == 1){
                        //AuthorOrDate = mArticleBody.split();
                        Heading1.add(mArticleBody);
                    }

                    else {

                        Heading1.add(" " + mArticleBody);
                        words += mArticleBody;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArticleBody.setText(words);
            //ArticleHeading.setText();
        }
    }
}
