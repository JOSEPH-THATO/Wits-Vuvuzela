package com.example.wits_vuvuzela_app;

public class Rating {

    private String ArticleID = "";
    private String Like = "No";
    private String Dislike = "No";

    public String getLike() {
        return Like;
    }

    public void setLike(String like) {
        Like = like;
    }

    public String getDislike() {
        return Dislike;
    }

    public void setDislike(String dislike) {
        Dislike = dislike;
    }

    public String getArticleID() {
        return ArticleID;
    }

    public void setArticleID(String articleID) {
        ArticleID = articleID;
    }
}
