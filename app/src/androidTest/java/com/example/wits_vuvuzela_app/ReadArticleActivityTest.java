package com.example.wits_vuvuzela_app;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
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

    }
    /*@Test
    public void ensureListViewIsPresent() throws Exception {
        ReadArticleActivity activity = readArticleActivityActivityRule.getActivity();
        View viewById = activity.findViewById(R.id.listview);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(ListView.class));
        ListView listView = (ListView) viewById;
        ListAdapter adapter = listView.getAdapter();
        assertThat(adapter, instanceOf(ArrayAdapter.class));
        assertThat(adapter.getCount(), greaterThan(15));

    }*/


    @After
    public void tearDown() throws Exception {
    }
}