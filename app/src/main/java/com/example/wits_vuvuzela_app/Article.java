package com.example.wits_vuvuzela_app;

import android.media.Image;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;


public class Article {

    private String ArticleImage = "no Image";
    private String ArticleTitle = " No Title";
    private String ArticleAutherName = "No Auther";
    private String ArticleComments = "User1-comment/User2-Comment";
    private String ArticleDateUploaded = "NoDate";
    private String ArticleLink = "No lInk";
    private String ArticleLikes = "0";
    private String ArticleDislikes = "0";
    private String ArticleLikedList = "User1/User2";
    private String ArticleDislikedList = "User1/User2";
    private String RateStatus = "none";
    private String NoArticleReplies = "0";

    public String getNoArticleReplies() {
        return NoArticleReplies;
    }

    public void setNoArticleReplies(String noArticleReplies) {
        NoArticleReplies = noArticleReplies;
    }

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

    public String getRateStatus() {
        return RateStatus;
    }

    public void setRateStatus(String rateStatus) {
        RateStatus = rateStatus;
    }

    public void setArticleAutherName(String articleAutherName) {
        ArticleAutherName = articleAutherName;
    }

    public String getArticleLikedList() {
        return ArticleLikedList;
    }

    public void setArticleLikedList(String articleLikedList) {
        ArticleLikedList = articleLikedList;
    }

    public String getArticleDislikedList() {
        return ArticleDislikedList;
    }

    public void setArticleDislikedList(String articleDislikedList) {
        ArticleDislikedList = articleDislikedList;
    }

    public String getArticleDateUploaded() {
        return ArticleDateUploaded;
    }

    public void setArticleDateUploaded(String articleDateUploaded) {
        ArticleDateUploaded = articleDateUploaded;
    }

    public String RemoveUserFromLikedArticleList(String User) {

        String[] LikedArticle = ArticleLikedList.split("/");

        String NewList = "User";

        for (int i = 0; i < LikedArticle.length; ++i) {
            if (User.equals(LikedArticle[i])) {
                continue;
            }
            NewList += ("/" + LikedArticle[i]);
        }

        ArticleLikedList = NewList;

        return ArticleLikedList;
    }

    public String RemoveUserFromDislikedArticleList(String User) {

        String[] DislikedArticle = ArticleDislikedList.split("/");

        String NewList = "User1";

        for (int i = 0; i < DislikedArticle.length; ++i) {
            if (User.equals(DislikedArticle[i])) {
                continue;
            }
            NewList += ("/" + DislikedArticle[i]);
        }

        ArticleDislikedList = NewList;

        return ArticleDislikedList;
    }


    public String AddUserToLikedList(String User) {
        if(ArticleLikedList.equals("")){
            ArticleLikedList+=User;
        }
        else {
            ArticleLikedList += ( "/" + User);
        }

        return ArticleLikedList;
    }

    public String AddUserToDislikedList(String User) {
        if (ArticleDislikedList.equals("")) {
            ArticleDislikedList += User;
        }
        else {
            ArticleDislikedList += ( "/" + User);
        }
        return ArticleDislikedList;
    }

    public void LikeAnArticle(String User) {

        int NumLikes = Integer.parseInt(ArticleLikes);
        int NumDislikes = Integer.parseInt(ArticleDislikes);

        if(ArticleLikedList.contains(User)){
            NumLikes -= 1;
            ArticleLikes = String.valueOf(NumLikes);
            ArticleLikedList = RemoveUserFromLikedArticleList(User);
        }

        else if(ArticleDislikedList.contains(User)){

            NumLikes+=1;
            NumDislikes-=1;
            ArticleLikes = String.valueOf(NumLikes);
            ArticleDislikes = String.valueOf(NumDislikes);
            ArticleLikedList = AddUserToLikedList(User);
            ArticleDislikedList = RemoveUserFromDislikedArticleList(User);
        }

       // if(!ArticleLikedList.contains(User) && !ArticleDislikedList.contains(User)){
        else{
            NumLikes+=1;
            ArticleLikedList = AddUserToLikedList(User);
            ArticleLikes = String.valueOf(NumLikes);
        }
    }

    public void DislikeAnArticle(String User){

        int NumLikes = Integer.parseInt(ArticleLikes);
        int NumDislikes = Integer.parseInt(ArticleDislikes);

        if(ArticleDislikedList.contains(User)){

            NumDislikes -= 1;
            ArticleDislikes = String.valueOf(NumDislikes);
            ArticleDislikedList = RemoveUserFromDislikedArticleList(User);
        }

        else if(ArticleLikedList.contains(User)){

            NumLikes -= 1;
            NumDislikes += 1;
            ArticleLikes = String.valueOf(NumLikes);
            ArticleDislikes = String.valueOf(NumDislikes);
            ArticleDislikedList = AddUserToDislikedList(User);
            ArticleLikedList = RemoveUserFromLikedArticleList(User);
        }

       // if(!ArticleLikedList.contains(User) && !ArticleDislikedList.contains(User)){
        else{
            NumDislikes+=1;
            ArticleDislikes = String.valueOf(NumDislikes);
            ArticleDislikedList = AddUserToDislikedList(User);
        }
    }
}
