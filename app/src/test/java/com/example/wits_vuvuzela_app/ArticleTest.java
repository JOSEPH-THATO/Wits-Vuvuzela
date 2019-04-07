package com.example.wits_vuvuzela_app;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArticleTest {

    @Test
    public void getArticleLikes() {

        String Input = "6";
        String Actual = "";
        String Expected = "6";

        Article article = new Article();
        article.setArticleLikes(Input);

        Actual = article.getArticleLikes();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setArticleLikes() {

        String Input = "10";

        Article article = new Article();

        article.setArticleLikes(Input);

        String Expected = "10";
        String Actual = article.getArticleLikes();

        assertEquals(Expected,Actual);

    }

    @Test
    public void getArticleDislikes() {

        String Input = "30";
        String Actual = "";
        String Expected = "30";

        Article article = new Article();
        article.setArticleDislikes(Input);

        Actual = article.getArticleDislikes();

        assertEquals(Expected,Actual);

    }

    @Test
    public void setArticleDislikes() {

        String Input = "40";

        Article article = new Article();

        article.setArticleDislikes(Input);

        String Expected = "40";
        String Actual = article.getArticleDislikes();

        assertEquals(Expected,Actual);

    }

    @Test
    public void getArticleComments() {

        String Input = "User1-Comment1/User2-Comment2";
        String Actual = "";
        String Expected = "User1-Comment1/User2-Comment2";

        Article article = new Article();
        article.setArticleComments(Input);

        Actual = article.getArticleComments();

        assertEquals(Expected,Actual);

    }

    @Test
    public void setArticleComments() {

        String Input = "Setting a new Comment";

        Article article = new Article();

        article.setArticleComments(Input);

        String Expected = "Setting a new Comment";
        String Actual = article.getArticleComments();

        assertEquals(Expected,Actual);

    }

    @Test
    public void getArticleLink() {

        String Input = "http://www.google.com";
        String Actual = "";
        String Expected = "http://www.google.com";

        Article article = new Article();
        article.setArticleLink(Input);

        Actual = article.getArticleLink();

        assertEquals(Expected,Actual);

    }

    @Test
    public void setArticleLink() {

        String Input = "http://www.google.com";

        Article article = new Article();

        article.setArticleLink(Input);

        String Expected = "http://www.google.com";
        String Actual = article.getArticleLink();

        assertEquals(Expected,Actual);

    }

    @Test
    public void getArticleImage() {

        String Input = "www.gmail.com/image.jpg";
        String Actual = "";
        String Expected = "www.gmail.com/image.jpg";

        Article article = new Article();
        article.setArticleTitle(Input);

        Actual = article.getArticleTitle();

        assertEquals(Expected,Actual);

    }

    @Test
    public void setArticleImage() {

        String Input = "http://www.google.com/image";

        Article article = new Article();

        article.setArticleImage(Input);

        String Expected = "http://www.google.com/image";
        String Actual = article.getArticleImage();

        assertEquals(Expected,Actual);

    }

    @Test
    public void getArticleTitle() {

        String Input = "Wits University Hunger Strike";
        String Actual = "";
        String Expected = "Wits University Hunger Strike";

        Article article = new Article();
        article.setArticleTitle(Input);

        Actual = article.getArticleTitle();

        assertEquals(Expected,Actual);

    }

    @Test
    public void setArticleTitle() {

        String Input = "Students Preparing For the June Exams";

        Article article = new Article();

        article.setArticleTitle(Input);

        String Expected = "Students Preparing For the June Exams";
        String Actual = article.getArticleTitle();

        assertEquals(Expected,Actual);

    }

    @Test
    public void getArticleAutherName() {

        String Input = "Patrick Jane";
        String Actual = "";
        String Expected = "Patrick Jane";

        Article article = new Article();
        article.setArticleAutherName(Input);

        Actual = article.getArticleAutherName();

        assertEquals(Expected,Actual);

    }

    @Test
    public void setArticleAutherName() {

        String Input = "Sheldon Cooper";

        Article article = new Article();

        article.setArticleAutherName(Input);

        String Expected = "Sheldon Cooper";
        String Actual = article.getArticleAutherName();

        assertEquals(Expected,Actual);

    }

    @Test
    public void getArticleDateUploaded() {

        String Input = "8 April 2019";
        String Actual = "";
        String Expected = "8 April 2019";

        Article article = new Article();
        article.setArticleDateUploaded(Input);

        Actual = article.getArticleDateUploaded();

        assertEquals(Expected,Actual);

    }

    @Test
    public void setArticleDateUploaded() {

        String Input = "29 April 2019";

        Article article = new Article();

        article.setArticleDateUploaded(Input);

        String Expected = "29 April 2019";
        String Actual = article.getArticleDateUploaded();

        assertEquals(Expected,Actual);

    }

    @Test
    public void addComment() {

        Article article = new Article();
        article.setArticleComments("");

        String Comment = "This is a sample Comment for an article that has no comment";
        String User = "Abdullah";

        article.AddComment(Comment,User);

        String Expected = "Abdullah-This is a sample Comment for an article that has no comment";
        String Actual = article.getArticleComments();

        assertEquals(Expected,Actual);

        Article article2 = new Article();

        String Comment2 = "This is a sample Comment for an article that already has comments";
        String User2 = "Francisco";

        String Expected2 = article2.getArticleComments() + "/Francisco-This is a sample Comment for an article that already has comments";

        article2.AddComment(Comment2,User2);

        String Actual2 = article2.getArticleComments();

        assertEquals(Expected2,Actual2);
    }
}