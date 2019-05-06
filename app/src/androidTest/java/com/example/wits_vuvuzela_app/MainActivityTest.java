package com.example.wits_vuvuzela_app;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    public  MainActivity mainActivity =null;


    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityTestRule.getActivity();
    }

    @Test
    public void MainTestLaunch(){



        View LoginTV = mainActivity.findViewById(R.id.loginID);
        assertNotNull(LoginTV);

        View EmailET = mainActivity.findViewById(R.id.EdtxtUsernameLogin);
        assertNotNull(EmailET);

        View PasswordET = mainActivity.findViewById(R.id.EdTxtPasswordLogin);
        assertNotNull(PasswordET);

        View LoginBtn = mainActivity.findViewById(R.id.BtnLogin);
        assertNotNull(LoginBtn);

        View ForgotPasswordTV = mainActivity.findViewById(R.id.loginID);
        assertNotNull(ForgotPasswordTV);

        View NoAccountTV = mainActivity.findViewById(R.id.txtViewForgotPasswordLogin);
        assertNotNull(NoAccountTV);

        View RegisterTV = mainActivity.findViewById(R.id.txtViewRegisterLogin);
        assertNotNull(RegisterTV);
    }
    @After
    public void tearDown() throws Exception {
        mainActivity=null;

    }
}