package com.example.wits_vuvuzela_app;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.*;

public class ReadArticleActivityTest {


    @Rule
    public ActivityTestRule<ReadArticleActivity> readArticleActivityActivityRule = new ActivityTestRule<>(ReadArticleActivity.class);
    public  ReadArticleActivity readArticleActivity =null;


    @Before
    public void setUp() throws Exception {
        readArticleActivity = readArticleActivityActivityRule.getActivity();
    }

    public static ViewAction handleConstraints(final ViewAction action, final Matcher<View> constraints)
    {
        return new ViewAction()
        {
            @Override
            public Matcher<View> getConstraints()
            {
                return constraints;
            }

            @Override
            public String getDescription()
            {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view)
            {
                action.perform(uiController, view);
            }
        };
    }

    @Test
    public void readArticleTest(){

        View readArticleHeading = (readArticleActivity).findViewById(R.id.ReadArticleHeading);
        assertNotNull(readArticleHeading);
        View likeNum = (readArticleActivity).findViewById(R.id.likeNum);
        assertNotNull(likeNum);
        View dislikeNum = (readArticleActivity).findViewById(R.id.dislikeNum);
        assertNotNull(dislikeNum);
        View commentANum = (readArticleActivity).findViewById(R.id.commentANum);
        assertNotNull(commentANum);
        View BackArrow = (readArticleActivity).findViewById(R.id.backArrow);
        assertNotNull(BackArrow);
        View likebtn = (readArticleActivity).findViewById(R.id.likebtn);
        assertNotNull(likebtn);
        View dislikebtn = (readArticleActivity).findViewById(R.id.dislikebtn);
        assertNotNull(dislikebtn);
        View commentIconBtn = readArticleActivity.findViewById(R.id.commentIconBtn);
        assertNotNull(commentIconBtn);
    }

    @Test
    public void TestBackButton() throws Exception {
        onView(withId(R.id.backArrow)).perform(click());
    }
    @Test
    public void TestLike() throws Exception{
        onView(withId(R.id.likebtn)).perform(click());
    }
    @Test
    public void TestDislike() throws Exception{
        onView(withId(R.id.dislikebtn)).perform(click());
    }
    @Test
    public void TestComment() throws Exception{
        onView(withId(R.id.commentIconBtn));
    }

    @After
    public void tearDown() throws Exception {
        readArticleActivity = null;
    }
}