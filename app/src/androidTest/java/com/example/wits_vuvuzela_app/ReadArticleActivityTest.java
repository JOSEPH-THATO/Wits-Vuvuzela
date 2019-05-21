package com.example.wits_vuvuzela_app;

import android.support.test.espresso.Espresso;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class ReadArticleActivityTest {

    @Before
    public void setUp() throws Exception {
    }

    @Rule
    public ActivityRule<ReadArticleActivity> readArticleActivityActivityRule = new ActivityRule<>(ReadArticleActivity.class);
    public  ReadArticleActivity readArticleActivity =null;

    public void readArticleTest(){
        Espresso.onView(withId(R.id.ReadArticleHeading)).check(matches(not(withText(""))));
        Espresso.onView(withId(R.id.ReadArticleBody)).check(matches(not(withText(""))));
    }

    @After
    public void tearDown() throws Exception {
    }
}