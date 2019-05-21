package com.example.wits_vuvuzela_app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class HomePageTest {

    @Rule
    public ActivityRule<HomePage> homePageActivityRule = new ActivityRule<>(HomePage.class);
    public  HomePage homepage =null;

    public IntentsTestRule<HomePage> homePageIntentsTestRule = new IntentsTestRule<>(
            HomePage.class);

    @Before
    public void setUp() throws Exception {
        homepage = homePageActivityRule.get();
    }

    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal()))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void testHomepage()
    {
        View imageview = (homepage).findViewById(R.id.imageView);
        onView(withId(R.id.newsfeed)).check(matches(not(withText(""))));
        onView(withId(R.id.listview)).check(matches(isDisplayed()));

        onView(withId(R.id.BtnLogin)).perform(click());

        /*intended(Matchers.allOf(
                hasAction(Intent.ACTION_CALL),

       */
    }

    @After
    public void tearDown() throws Exception {
    }
}