package com.example.wits_vuvuzela_app;

import android.media.Image;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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

    public void AddComment(String NewComment, String User,String Comments) {

        if (Comments.equals("")) {
            Comments = User + "-" + NewComment;
        } else {
            Comments = Comments + "/" + User + "-" + NewComment;
        }

        ArticleComments = Comments;
    }

    public boolean ArticleAlreadyLiked(String User,String LikedList) {

        String[] LikedArticle = LikedList.split("/");

        for (int i = 0; i < LikedArticle.length; ++i) {
            if (User.equals(LikedArticle[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean ArticleAlreadyDisliked(String User,String DislikedList) {

        String[] DislikedArticle = DislikedList.split("/");

        for (int i = 0; i < DislikedArticle.length; ++i) {
            if (User.equals(DislikedArticle[i])) {
                return true;
            }
        }
        return false;
    }

    public void RemoveUserFromLikedArticleList(String User,String LikedList) {

        String[] LikedArticle = LikedList.split("/");

        String NewList = "User";

        for (int i = 0; i < LikedArticle.length; ++i) {
            if (User.equals(LikedArticle[i])) {
                continue;
            }

            NewList += ("/" + LikedArticle[i]);
        }

        ArticleLikedList = NewList;
    }

    public void RemoveUserFromDislikedArticleList(String User,String DislikedList) {

        String[] DislikedArticle = DislikedList.split("/");

        String NewList = "User1";

        for (int i = 0; i < DislikedArticle.length; ++i) {
            if (User.equals(DislikedArticle[i])) {
                continue;
            }

            NewList += ("/" + DislikedArticle[i]);
        }

        ArticleDislikedList = NewList;
    }


    public void AddUserToLikedList(String User,String LikedList) {
        if(LikedList.equals("")){
            LikedList+=User;
        }
        else {
            LikedList += ( "/" + User);
        }

        ArticleLikedList = LikedList;
    }

    public void AddUserToDislikedList(String User,String DislikedList) {
        if (DislikedList.equals("")) {
            DislikedList += User;
        }
        else {
            DislikedList += ( "/" + User);
        }
        ArticleDislikedList = DislikedList;
    }

    public void LikeAnArticle(String User,int likes,int dislikes,String LikedList,String DislikedList) {

        int NumLikes = likes;
        int NumDislikes = dislikes;

        if(!ArticleAlreadyLiked(User,LikedList) && !ArticleAlreadyDisliked(User,DislikedList)){
            NumLikes+=1;
            AddUserToLikedList(User,LikedList);
            ArticleLikes = String.valueOf(NumLikes);
        }

        else if(ArticleAlreadyLiked(User,LikedList)){

            if(likes == 0){
                ArticleLikes = String.valueOf(NumLikes);
                RemoveUserFromLikedArticleList(User,LikedList);
            }

            else {
                NumLikes -= 1;
                ArticleLikes = String.valueOf(NumLikes);
                RemoveUserFromLikedArticleList(User,LikedList);
            }
        }

        else if(ArticleAlreadyDisliked(User,DislikedList)){

            NumLikes+=1;
            NumDislikes-=1;
            ArticleLikes = String.valueOf(NumLikes);
            ArticleDislikes = String.valueOf(NumDislikes);
            AddUserToLikedList(User,LikedList);
            RemoveUserFromDislikedArticleList(User,DislikedList);
        }
    }

    public void DislikeAnArticle(String User,int likes,int dislikes,String LikedList,String DislikedList){

        int NumLikes = likes;
        int NumDislikes = dislikes;

        if(!ArticleAlreadyLiked(User,LikedList) && !ArticleAlreadyDisliked(User,DislikedList)){
            NumDislikes+=1;
            ArticleDislikes = String.valueOf(NumDislikes);
            AddUserToDislikedList(User,DislikedList);
        }

        else if(ArticleAlreadyDisliked(User,DislikedList)){

            if(dislikes == 0){
                ArticleDislikes = String.valueOf(NumDislikes);
                RemoveUserFromDislikedArticleList(User,DislikedList);
            }
            else {
                NumDislikes -= 1;
                ArticleDislikes = String.valueOf(NumDislikes);
                RemoveUserFromDislikedArticleList(User,DislikedList);
            }
        }

        else if(ArticleAlreadyLiked(User,LikedList)){

            NumLikes -= 1;
            NumDislikes += 1;
            ArticleLikes = String.valueOf(NumLikes);
            ArticleDislikes = String.valueOf(NumDislikes);
            AddUserToDislikedList(User,DislikedList);
            RemoveUserFromLikedArticleList(User,LikedList);
        }
    }
}
