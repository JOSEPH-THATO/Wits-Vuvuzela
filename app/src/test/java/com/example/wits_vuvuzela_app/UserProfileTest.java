package com.example.wits_vuvuzela_app;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserProfileTest {

    @Test
    public void getUser_fName() {

        String Input = "Abdullah";
        String Actual = "";
        String Expected = "Abdullah";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_fName(Input);

        Actual = userProfile.getUser_fName();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setUser_fName() {

        String Input = "Abdullah";
        String Expected = "Abdullah";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_fName(Input);

        String Actual = userProfile.getUser_fName();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getUser_lName() {

        String Input = "Francisco";
        String Actual = "";
        String Expected = "Francisco";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_lName(Input);

        Actual = userProfile.getUser_lName();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setUser_lName() {

        String Input = "Francisco";
        String Expected = "Francisco";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_lName(Input);

        String Actual = userProfile.getUser_lName();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getUser_username() {

        String Input = "AbdullahFrancisco";
        String Actual = "";
        String Expected = "AbdullahFrancisco";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_username(Input);

        Actual = userProfile.getUser_username();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setUser_username() {

        String Input = "AbdullahFrancisco";
        String Expected = "AbdullahFrancisco";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_username(Input);

        String Actual = userProfile.getUser_username();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getUser_email() {

        String Input = "1758200@students.wits.ac.za";
        String Actual = "";
        String Expected = "1758200@students.wits.ac.za";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_email(Input);

        Actual = userProfile.getUser_email();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setUser_email() {

        String Input = "1758200@students.wits.ac.za";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_email(Input);

        String Expected = "1758200@students.wits.ac.za";
        String Actual = userProfile.getUser_email();

        assertEquals(Expected,Actual);
    }

    @Test
    public void getUser_password() {

        String Input = "password";
        String Actual = "";
        String Expected = "password";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_password(Input);

        Actual = userProfile.getUser_password();

        assertEquals(Expected,Actual);
    }

    @Test
    public void setUser_password() {

        String Input = "password";
        String Expected = "password";

        UserProfile userProfile = new UserProfile();

        userProfile.setUser_password(Input);

        String Actual = userProfile.getUser_password();

        assertEquals(Expected,Actual);
    }
}