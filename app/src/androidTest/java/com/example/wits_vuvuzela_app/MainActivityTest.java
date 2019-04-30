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
        View view = mainActivity.findViewById(R.id.loginID);
        assertNotNull(view);
    }
    @After
    public void tearDown() throws Exception {
        mainActivity=null;

    }
}