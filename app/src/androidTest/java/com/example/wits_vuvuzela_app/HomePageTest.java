package com.example.wits_vuvuzela_app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
//import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.support.test.rule.ActivityTestRule;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.hamcrest.CoreMatchers;
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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class HomePageTest {

    @Rule
    public ActivityTestRule<HomePage> homePageActivityRule = new ActivityTestRule<HomePage>(HomePage.class){
        @Override
        protected Intent getActivityIntent(){
            InstrumentationRegistry.getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("Email","UserName");
            return intent;
        }
    };
    public  HomePage homepage =null;


    @Before
    public void setUp() throws Exception {
        homepage = homePageActivityRule.getActivity();
    }


    @Test
    public void testHomepage() throws Exception
    {
        View newsfeed = (homepage).findViewById(R.id.newsfeed);
        assertNotNull(newsfeed);
        View progressBar = (homepage).findViewById(R.id.HomePageBar);
        assertNotNull(progressBar);

    }
/*
    @Test
    public void ensureListViewPresent() throws Exception{
        View listview = homepage.findViewById(R.id.listview);
        assertThat(listview,notNullValue());
        assertThat(listview, CoreMatchers.<View>instanceOf(ListView.class));
        ListView Listview = (ListView) listview;
        ListAdapter adapter = Listview.getAdapter();
        assertThat(adapter,notNullValue());
        assertThat(adapter.getCount(),greaterThanOrEqualTo(1));
    }
*/
    @After
    public void tearDown() throws Exception {
        homepage = null;
    }
}