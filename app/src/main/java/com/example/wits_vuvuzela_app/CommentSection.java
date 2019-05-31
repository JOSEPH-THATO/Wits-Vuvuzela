package com.example.wits_vuvuzela_app;

public class CommentSection {

    private String CommentKey = "";
    private String CommentID = "abcdefgh";
    private String Comment = "Sample Comment";
    private String UserLike = "";
    private String UserDislike = "";
    private String UserComment = "";
    private String UserName = "Abdullah";
    private String CommentTime = "NoDate";
    private String CommentRate = "0";
    private String NoCommentLikes = "0";
    private String NoCommentDislikes = "0";
    private String NoReplies = "0";
    private String CommentLikedList =  "User1/User2";
    private String CommentDislikedList =  "User1/User2";
    private String CommentToken =  "";

    public String getCommentKey() {
        return CommentKey;
    }

    public void setCommentKey(String commentKey) {
        CommentKey = commentKey;
    }

    public String getUserComment() {
        return UserComment;
    }

    public void setUserComment(String userComment) {
        UserComment = userComment;
    }

    public String getUserLike() {
        return UserLike;
    }

    public void setUserLike(String userLike) {
        UserLike = userLike;
    }

    public String getUserDislike() {
        return UserDislike;
    }

    public void setUserDislike(String userDislike) {
        UserDislike = userDislike;
    }

    public String getCommentToken() {
        return CommentToken;
    }

    public void setCommentToken(String commentToken) {
        CommentToken = commentToken;
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

    public void LikeComment(String User){

        int NoLikes = Integer.parseInt(NoCommentLikes);
        int NoDislikes = Integer.parseInt(NoCommentDislikes);

        if(CommentLikedList.contains(User)){
            NoLikes-=1;
            UserLike = "";
            CommentLikedList = RemoveUserFromLikedCommentList(User);
        }

        else if(CommentDislikedList.contains(User)){
            NoDislikes -= 1;
            NoLikes+=1;
            UserLike = User;
            UserDislike = "";
            CommentDislikedList = RemoveUserFromDislikedCommentList(User);
            CommentLikedList = AddUserToLikedList(User);
        }

        else{
            NoLikes+=1;
            UserLike = User;
            CommentLikedList = AddUserToLikedList(User);
        }

        NoCommentLikes = String.valueOf(NoLikes);
        NoCommentDislikes = String.valueOf(NoDislikes);

    }

    public void DislikeComment(String User){

        int NoLikes = Integer.parseInt(NoCommentLikes);
        int NoDislikes = Integer.parseInt(NoCommentDislikes);


        if(CommentDislikedList.contains(User)){
            NoDislikes-=1;
            UserDislike = "";
            CommentDislikedList = RemoveUserFromDislikedCommentList(User);
        }

        else if(CommentLikedList.contains(User)){
            NoDislikes += 1;
            NoLikes -=1;
            UserDislike = User;
            UserLike="";
            CommentDislikedList = AddUserToDislikedList(User);
            CommentLikedList = RemoveUserFromLikedCommentList(User);
        }

        else{
            NoDislikes+=1;
            UserDislike = User;
            CommentDislikedList = AddUserToDislikedList(User);
        }

        NoCommentLikes = String.valueOf(NoLikes);
        NoCommentDislikes = String.valueOf(NoDislikes);

    }

    public String AddUserToLikedList(String User) {

       // if(CommentLikedList.equals("")){
       //     CommentLikedList+=User;
       // }
       // else {
          CommentLikedList += ( "/" + User);
       // }

        return CommentLikedList;
    }

    public String AddUserToDislikedList(String User) {
      //  if (CommentDislikedList.equals("")) {
      //      CommentDislikedList += User;
      //  }
      //  else {
            CommentDislikedList += ( "/" + User);
      //  }
        return CommentDislikedList;
    }

    public String RemoveUserFromLikedCommentList(String User) {

        String[] LikedArticle = CommentLikedList.split("/");

        String NewList = "User";

        for (int i = 0; i < LikedArticle.length; ++i) {
            if (User.equals(LikedArticle[i])) {
                continue;
            }

            NewList += ("/" + LikedArticle[i]);
        }

        return NewList;
    }

    public String RemoveUserFromDislikedCommentList(String User) {

        String[] DislikedComment = CommentDislikedList.split("/");

        String NewList = "User1";

        for (int i = 0; i < DislikedComment.length; ++i) {
            if (User.equals(DislikedComment[i])) {
                continue;
            }

            NewList += ("/" + DislikedComment[i]);
        }
        return NewList;
    }
}
/*
*             convertView.setLayoutParams(params);

 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            .setLayoutParams(params);
 *
* */