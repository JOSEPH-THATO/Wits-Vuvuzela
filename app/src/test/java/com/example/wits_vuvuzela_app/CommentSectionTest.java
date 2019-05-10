package com.example.wits_vuvuzela_app;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommentSectionTest {

    @Test
    public void getCommentLikedList() {

        String Actual = "";
        String Expected = "User1/User2";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getCommentLikedList();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setCommentLikedList() {

        String Input = "User1/User2";
        String Actual = "";
        String Expected = "User1/User2";

        CommentSection commentSection = new CommentSection();

        commentSection.setCommentLikedList(Input);

        Actual = commentSection.getCommentLikedList();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getCommentDislikedList() {

        String Actual = "";
        String Expected = "User1/User2";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getCommentDislikedList();

        assertEquals(Expected,Actual);

    }

    @Test
    public void setCommentDislikedList() {

        String Input = "User1/User2";
        String Actual = "";
        String Expected = "User1/User2";

        CommentSection commentSection = new CommentSection();

        commentSection.setCommentDislikedList(Input);

        Actual = commentSection.getCommentDislikedList();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getNoCommentLikes() {

        String Actual = "";
        String Expected = "0";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getNoCommentLikes();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setNoCommentLikes() {

        String Input = "8";
        String Actual = "";
        String Expected = "8";

        CommentSection commentSection = new CommentSection();

        commentSection.setNoCommentLikes(Input);

        Actual = commentSection.getNoCommentLikes();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getNoCommentDislikes() {

        String Actual = "";
        String Expected = "0";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getNoCommentDislikes();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setNoCommentDislikes() {

        String Input = "2";
        String Actual = "";
        String Expected = "2";

        CommentSection commentSection = new CommentSection();

        commentSection.setNoCommentDislikes(Input);

        Actual = commentSection.getNoCommentDislikes();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getNoReplies() {

        String Actual = "";
        String Expected = "0";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getNoReplies();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setNoReplies() {
        String Input = "4";
        String Actual = "";
        String Expected = "4";

        CommentSection commentSection = new CommentSection();

        commentSection.setNoReplies(Input);

        Actual = commentSection.getNoReplies();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getCommentRate() {

        String Actual = "";
        String Expected = "None";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getCommentRate();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setCommentRate() {

        String Input = "Like";
        String Actual = "";
        String Expected = "Like";

        CommentSection commentSection = new CommentSection();

        commentSection.setCommentRate(Input);

        Actual = commentSection.getCommentRate();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getUserName() {

        String Actual = "";
        String Expected = "Abdullah";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getUserName();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setUserName() {

        String Input = "Username";
        String Actual = "";
        String Expected = "Username";

        CommentSection commentSection = new CommentSection();

        commentSection.setUserName(Input);

        Actual = commentSection.getUserName();

        assertEquals(Expected,Actual);

    }

    @Test
    public void getComment() {

        String Actual = "";
        String Expected = "Sample Comment";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getComment();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setComment() {

        String Input = "A sample Comment";
        String Actual = "";
        String Expected = "A sample Comment";

        CommentSection commentSection = new CommentSection();

        commentSection.setComment(Input);

        Actual = commentSection.getComment();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getCommentID() {

        String Actual = "";
        String Expected = "abcdefgh";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getCommentID();

        assertEquals(Expected,Actual);

    }

    @Test
    public void setCommentID() {

        String Input = "xyz";
        String Actual = "";
        String Expected = "xyz";

        CommentSection commentSection = new CommentSection();

        commentSection.setCommentID(Input);

        Actual = commentSection.getCommentID();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getCommentTime() {

        String Actual = "";
        String Expected = "NoDate";

        CommentSection commentSection = new CommentSection();

        Actual = commentSection.getCommentTime();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setCommentTime() {

        String Input = "15:00";

        CommentSection commentSection = new CommentSection();

        commentSection.setCommentTime(Input);

        String Expected = "15:00";
        String Actual = commentSection.getCommentTime();

        assertEquals(Expected,Actual);
    }

    @Test
    public void likeComment() {

        String User = "Abdullah";

        CommentSection commentSection = new CommentSection();

        commentSection.setNoCommentLikes("9");
        commentSection.setNoCommentDislikes("9");
        commentSection.setCommentLikedList("Abdullah/Francisco/Mansur");
        commentSection.setCommentDislikedList("Lionel/Messi");

        commentSection.LikeComment(User);

        String ActualNumLikes = commentSection.getNoCommentLikes();
        String ActualNumDislikes = commentSection.getNoCommentDislikes();
        String ActualLikeList = commentSection.getCommentLikedList();
        String ActualDislikeList = commentSection.getCommentDislikedList();

        String ExpectedNumLikes = "8";
        String ExpectedNumDislikes = "9";
        String ExpectedLikeList = "User/Francisco/Mansur";
        String ExpectedDislikeList = "Lionel/Messi";

        assertEquals(ExpectedNumLikes,ActualNumLikes);
        assertEquals(ExpectedNumDislikes,ActualNumDislikes);
        assertEquals(ExpectedLikeList,ActualLikeList);
        assertEquals(ExpectedDislikeList,ActualDislikeList);

        String User2 = "Abdullah";

        CommentSection commentSection2 = new CommentSection();

        commentSection2.setNoCommentLikes("9");
        commentSection2.setNoCommentDislikes("9");
        commentSection2.setCommentLikedList("Francisco/Mansur");
        commentSection2.setCommentDislikedList("Lionel/Messi");

        commentSection2.LikeComment(User2);

        String ActualNumLikes2 = commentSection2.getNoCommentLikes();
        String ActualNumDislikes2 = commentSection2.getNoCommentDislikes();
        String ActualLikeList2 = commentSection2.getCommentLikedList();
        String ActualDislikeList2 = commentSection2.getCommentDislikedList();

        String ExpectedNumLikes2 = "10";
        String ExpectedNumDislikes2 = "9";
        String ExpectedLikeList2 = "Francisco/Mansur/Abdullah";
        String ExpectedDislikeList2 = "Lionel/Messi";

        assertEquals(ExpectedNumLikes2,ActualNumLikes2);
        assertEquals(ExpectedNumDislikes2,ActualNumDislikes2);
        assertEquals(ExpectedLikeList2,ActualLikeList2);
        assertEquals(ExpectedDislikeList2,ActualDislikeList2);

        String User3 = "Abdullah";

        CommentSection commentSection3 = new CommentSection();

        commentSection3.setNoCommentLikes("9");
        commentSection3.setNoCommentDislikes("9");
        commentSection3.setCommentLikedList("Francisco/Mansur");
        commentSection3.setCommentDislikedList("Lionel/Messi/Abdullah");

        commentSection3.LikeComment(User3);

        String ActualNumLikes3 = commentSection3.getNoCommentLikes();
        String ActualNumDislikes3 = commentSection3.getNoCommentDislikes();
        String ActualLikeList3 = commentSection3.getCommentLikedList();
        String ActualDislikeList3 = commentSection3.getCommentDislikedList();

        String ExpectedNumLikes3 = "10";
        String ExpectedNumDislikes3 = "8";
        String ExpectedLikeList3 = "Francisco/Mansur/Abdullah";
        String ExpectedDislikeList3 = "User1/Lionel/Messi";

        assertEquals(ExpectedNumLikes3,ActualNumLikes3);
        assertEquals(ExpectedNumDislikes3,ActualNumDislikes3);
        assertEquals(ExpectedLikeList3,ActualLikeList3);
        assertEquals(ExpectedDislikeList3,ActualDislikeList3);

    }

    @Test
    public void dislikeComment() {

        String User = "Abdullah";

        CommentSection commentSection = new CommentSection();

        commentSection.setNoCommentLikes("9");
        commentSection.setNoCommentDislikes("9");
        commentSection.setCommentLikedList("Francisco/Mansur");
        commentSection.setCommentDislikedList("Lionel/Messi/Abdullah");

        commentSection.DislikeComment(User);

        String ActualNumLikes = commentSection.getNoCommentLikes();
        String ActualNumDislikes = commentSection.getNoCommentDislikes();
        String ActualLikeList = commentSection.getCommentLikedList();
        String ActualDislikeList = commentSection.getCommentDislikedList();

        String ExpectedNumLikes = "9";
        String ExpectedNumDislikes = "8";
        String ExpectedLikeList = "Francisco/Mansur";
        String ExpectedDislikeList = "User1/Lionel/Messi";

        assertEquals(ExpectedNumLikes,ActualNumLikes);
        assertEquals(ExpectedNumDislikes,ActualNumDislikes);
        assertEquals(ExpectedLikeList,ActualLikeList);
        assertEquals(ExpectedDislikeList,ActualDislikeList);

        String User2 = "Abdullah";

        CommentSection commentSection2 = new CommentSection();

        commentSection2.setNoCommentLikes("9");
        commentSection2.setNoCommentDislikes("9");
        commentSection2.setCommentLikedList("Francisco/Mansur");
        commentSection2.setCommentDislikedList("Lionel/Messi");

        commentSection2.DislikeComment(User2);

        String ActualNumLikes2 = commentSection2.getNoCommentLikes();
        String ActualNumDislikes2 = commentSection2.getNoCommentDislikes();
        String ActualLikeList2 = commentSection2.getCommentLikedList();
        String ActualDislikeList2 = commentSection2.getCommentDislikedList();

        String ExpectedNumLikes2 = "9";
        String ExpectedNumDislikes2 = "10";
        String ExpectedLikeList2 = "Francisco/Mansur";
        String ExpectedDislikeList2 = "Lionel/Messi/Abdullah";

        assertEquals(ExpectedNumLikes2,ActualNumLikes2);
        assertEquals(ExpectedNumDislikes2,ActualNumDislikes2);
        assertEquals(ExpectedLikeList2,ActualLikeList2);
        assertEquals(ExpectedDislikeList2,ActualDislikeList2);

        String User3 = "Abdullah";

        CommentSection commentSection3 = new CommentSection();

        commentSection3.setNoCommentLikes("9");
        commentSection3.setNoCommentDislikes("9");
        commentSection3.setCommentLikedList("Francisco/Mansur/Abdullah");
        commentSection3.setCommentDislikedList("Lionel/Messi");

        commentSection3.DislikeComment(User3);

        String ActualNumLikes3 = commentSection3.getNoCommentLikes();
        String ActualNumDislikes3 = commentSection3.getNoCommentDislikes();
        String ActualLikeList3 = commentSection3.getCommentLikedList();
        String ActualDislikeList3 = commentSection3.getCommentDislikedList();

        String ExpectedNumLikes3 = "8";
        String ExpectedNumDislikes3 = "10";
        String ExpectedLikeList3 = "User/Francisco/Mansur";
        String ExpectedDislikeList3 = "Lionel/Messi/Abdullah";

        assertEquals(ExpectedNumLikes3,ActualNumLikes3);
        assertEquals(ExpectedNumDislikes3,ActualNumDislikes3);
        assertEquals(ExpectedLikeList3,ActualLikeList3);
        assertEquals(ExpectedDislikeList3,ActualDislikeList3);

    }

    @Test
    public void addUserToLikedList() {

        String User = "Abdullah";

        CommentSection commentSection = new CommentSection();

        commentSection.setCommentLikedList("Francisco/Mansur");

        commentSection.AddUserToLikedList(User);

        String Actual = commentSection.getCommentLikedList();
        String Expected = "Francisco/Mansur/Abdullah";

        assertEquals(Expected,Actual);

        String User2 = "James";

        CommentSection commentSection2 = new CommentSection();

        commentSection2.setCommentLikedList("");

        commentSection2.AddUserToLikedList(User2);

        String Actual2 = commentSection2.getCommentLikedList();

        String Expected2 = "/James";

        assertEquals(Expected2,Actual2);

    }

    @Test
    public void addUserToDislikedList() {

        String User = "Abdullah";

        CommentSection commentSection = new CommentSection();

        commentSection.setCommentDislikedList("Francisco/Mansur");

        commentSection.AddUserToDislikedList(User);

        String Actual = commentSection.getCommentDislikedList();
        String Expected = "Francisco/Mansur/Abdullah";

        assertEquals(Expected,Actual);

        String User2 = "James";

        CommentSection commentSection2 = new CommentSection();

        commentSection2.setCommentDislikedList("");

        commentSection2.AddUserToDislikedList(User2);

        String Actual2 = commentSection2.getCommentDislikedList();

        String Expected2 = "/James";

        assertEquals(Expected2,Actual2);
    }

    @Test
    public void removeUserFromLikedCommentList() {

        String User = "Sheldon";

        CommentSection commentSection = new CommentSection();

        commentSection.setCommentLikedList("Sheldon/Cooper/Leornard");

        String Actual = commentSection.RemoveUserFromLikedCommentList(User);

        String Expected = "User/Cooper/Leornard";

        assertEquals(Expected,Actual);

        String User2 = "Cooper";

        CommentSection commentSection1 = new CommentSection();

        commentSection1.setCommentLikedList("Cooper");

        String Actual2 = commentSection1.RemoveUserFromLikedCommentList(User2);

        String Expected2 = "User";

        assertEquals(Expected2,Actual2);
    }

    @Test
    public void removeUserFromDislikedCommentList() {

        String User = "Sheldon";

        CommentSection commentSection = new CommentSection();

        commentSection.setCommentDislikedList("Sheldon/Cooper/Leornard");

        String Actual = commentSection.RemoveUserFromDislikedCommentList(User);

        String Expected = "User1/Cooper/Leornard";

        assertEquals(Expected,Actual);

        String User2 = "Cooper";

        CommentSection commentSection1 = new CommentSection();

        commentSection1.setCommentDislikedList("Cooper");

        String Actual2 = commentSection1.RemoveUserFromDislikedCommentList(User2);

        String Expected2 = "User1";

        assertEquals(Expected2,Actual2);
    }
}