package com.example.wits_vuvuzela_app;

public class CommentSection {

    private String ArticleID = "";
    private String CommentID = "";
    private String Comment = "";
    private String UserName = "Abdullah";
    private String Replys = "User1-comment/User2-Comment";
    private String CommentTime = "NoDate";
    private String CommmentLikes = "0";
    private String CommentDislikes = "0";
    private String CommentLikedList = "User1/User2";
    private String CommentDislikedList = "User1/User2";

    public String getUserName() {
        return UserName;
    }   

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getArticleID() {
        return ArticleID;
    }

    public void setArticleID(String articleID) {
        ArticleID = articleID;
    }

    public String getCommentID() {
        return CommentID;
    }

    public void setCommentID(String commentID) {
        CommentID = commentID;
    }

    public String getReplys() {
        return Replys;
    }

    public void setReplys(String replys) {
        Replys = replys;
    }

    public String getCommentTime() {
        return CommentTime;
    }

    public void setCommentTime(String commentTime) {
        CommentTime = commentTime;
    }

    public String getCommmentLikes() {
        return CommmentLikes;
    }

    public void setCommmentLikes(String commmentLikes) {
        CommmentLikes = commmentLikes;
    }

    public String getCommentDislikes() {
        return CommentDislikes;
    }

    public void setCommentDislikes(String commentDislikes) {
        CommentDislikes = commentDislikes;
    }

    public String getCommentLikedList() {
        return CommentLikedList;
    }

    public void setCommentLikedList(String commentLikedList) {
        CommentLikedList = commentLikedList;
    }

    public String getCommentDislikedList() {
        return CommentDislikedList;
    }

    public void setCommentDislikedList(String commentDislikedList) {
        CommentDislikedList = commentDislikedList;
    }
}
