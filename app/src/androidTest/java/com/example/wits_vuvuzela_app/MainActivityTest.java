package com.example.wits_vuvuzela_app;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    public  MainActivity mainActivity =null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(HomePage.class.getName(),null,false);
    private String UEmail = "1503246@students.wits.ac.za";
    private String Password = "123456aB";
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

        View Progress= mainActivity.findViewById(R.id.progressBarLog);
        assertNotNull(Progress);

    }
    @Test
    public void testLaunchofHomePageonClick(){
        onView(withId(R.id.EdtxtUsernameLogin)).perform(replaceText(UEmail));
        onView(withId(R.id.EdTxtPasswordLogin)).perform(replaceText(Password));
        assertNotNull(mainActivity.findViewById(R.id.BtnLogin));
        onView(withId(R.id.BtnLogin)).perform(click());
/*
        Activity HomepageActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,500);
        assertNotNull(HomepageActivity);
        HomepageActivity.finish();
*/
    }

    @After
    public void tearDown() throws Exception {
        mainActivity=null;

    }
}