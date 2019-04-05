package com.example.wits_vuvuzela_app;

import android.media.Image;

import java.util.ArrayList;
import java.util.Date;

public class Article {

    private String ArticleImage="no Image";
    private String ArticleTitle=" No Title";
    private String ArticleAutherName="No Auther";
    private String ArticleComments= "Comment1/Comment2/Comment3";
    private String ArticleDateUploaded = "NoDate";
    private String ArticleLink = "No lInk";
    private String ArticleLikes = "0";
    private String ArticleDislikes = "4";

    public String getArticleLikes() {
        return ArticleLikes;
    }

    public void setArticleLikes(String articleLikes) {
        ArticleLikes = articleLikes;
    }

    public String getArticleDislikes() {
        return ArticleDislikes;
    }

    public void setArticleDislikes(String articleDislikes) {
        ArticleDislikes = articleDislikes;
    }

    public String getArticleComments() {
        return ArticleComments;
    }

    public void setArticleComments(String articleComments) {
        ArticleComments = articleComments;
    }

    public String getArticleLink() {
        return ArticleLink;
    }

    public void setArticleLink(String articleLink) {
        ArticleLink = articleLink;
    }

    public String getArticleImage() {
        return ArticleImage;
    }

    public void setArticleImage(String articleImage) {
        ArticleImage = articleImage;
    }

    public String getArticleTitle() {
        return ArticleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        ArticleTitle = articleTitle;
    }

    public String getArticleAutherName() {
        return ArticleAutherName;
    }

    public void setArticleAutherName(String articleAutherName) {
        ArticleAutherName = articleAutherName;
    }

    public String getArticleDateUploaded() {
        return ArticleDateUploaded;
    }

    public void setArticleDateUploaded(String articleDateUploaded) {
        ArticleDateUploaded = articleDateUploaded;
    }

}
