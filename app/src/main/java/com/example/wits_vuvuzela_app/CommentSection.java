package com.example.wits_vuvuzela_app;

public class CommentSection {

    private String CommentID = "";
    private String Comment = "";
    private String UserName = "Abdullah";
    private String CommentTime = "NoDate";
    private String CommentRate = "None";
    private String NoCommentLikes = "0";
    private String NoCommentDislikes = "0";
    private String NoReplies = "0";
    private String CommentLikedList =  "User1/User2";
    private String CommentDislikedList =  "User1/User2";

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

    public String getNoCommentLikes() {
        return NoCommentLikes;
    }

    public void setNoCommentLikes(String noCommentLikes) {
        NoCommentLikes = noCommentLikes;
    }

    public String getNoCommentDislikes() {
        return NoCommentDislikes;
    }

    public void setNoCommentDislikes(String noCommentDislikes) {
        NoCommentDislikes = noCommentDislikes;
    }

    public String getNoReplies() {
        return NoReplies;
    }

    public void setNoReplies(String noReplies) {
        NoReplies = noReplies;
    }

    public String getCommentRate() {
        return CommentRate;
    }

    public void setCommentRate(String commentRate) {
        CommentRate = commentRate;
    }

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

    public String getCommentID() {
        return CommentID;
    }

    public void setCommentID(String commentID) {
        CommentID = commentID;
    }

    public String getCommentTime() {
        return CommentTime;
    }

    public void setCommentTime(String commentTime) {
        CommentTime = commentTime;
    }

}
