package com.example.wits_vuvuzela_app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class CommentsActivityTest {


            @Rule
            public ActivityTestRule<CommentsActivity> commentsActivityTestActivityTestRule = new ActivityTestRule<>(CommentsActivity.class);
            public CommentsActivity commentsActivity = null;

            private String comment = "SomeComment";

            @Before
            public void setUp() throws Exception {
                commentsActivity = commentsActivityTestActivityTestRule.getActivity();
            }

    /*public static ViewAction handleConstraints(final ViewAction action, final Matcher<View> constraints)
    {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                 action.perform(uiController, view );
            }
        };
    }*/

            @Test
            public void TestViews() {
                CommentsActivity commentsActivity = new CommentsActivity();
                if (commentsActivity.customAdapter1 != null) {
                    View listview = (commentsActivity).findViewById(R.id.listview);
                    assertNotNull(listview);
                }

            }

            @Test
            public void TestCommentsActivity() {

                View backArrow = (commentsActivity).findViewById(R.id.backArrow);
                assertNotNull(backArrow);
                View txtComment = (commentsActivity).findViewById(R.id.txtcomment);
                assertNotNull(txtComment);
                View CommentTitle = (commentsActivity).findViewById(R.id.CommentTitle);
                assertNotNull(CommentTitle);





        /*
       Espresso.onView(withId(R.id.txtcomment)).check(matches((withText(""))));
      Espresso.onView(withId(R.id.listview)).check(matches(isDisplayed()));
      Espresso.closeSoftKeyboard();;
     */

            }


            @Test
            public void CommentEdit() throws Exception {
                onView(withId(R.id.editComments)).perform(replaceText(comment));
                View editComments = (commentsActivity).findViewById(R.id.editComments);
                assertNotNull(editComments);
                onView(withId(R.id.commentBtns)).perform(click());

            }

            @After
            public void tearDown() throws Exception {
                commentsActivity = null;

            }
        }

