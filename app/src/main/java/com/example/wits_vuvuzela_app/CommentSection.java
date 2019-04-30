package com.example.wits_vuvuzela_app;

public class CommentSection {

    private String CommentID = "";
    private String Comment = "";
    private String UserName = "Abdullah";
    private String CommentTime = "NoDate";
    private String CommentRate = "Like";

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
