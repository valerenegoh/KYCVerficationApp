package com.example.asus.remotekyc;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ResetPassTest {
    @Rule
    public ActivityTestRule<resetpassword> mActivityTestRule = new ActivityTestRule<>(resetpassword.class);

    @Test //Success Case
    public void Test_success(){
        String email = "tanasus1993@gmail.com";

        //find the email address edit text and type in the email address
        onView(withId(R.id.editemail)).perform(typeText(email), closeSoftKeyboard());

        //click the reset button
        onView(withId(R.id.reset)).perform(click());

        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        //Check whether there the "An email has been sent to reset your password" message appears, if not the test is a fail
        //Expected Result: Pass
        resetpassword activity = mActivityTestRule.getActivity();
        onView(withText(R.string.reset_success)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    @Test //Failure Case
    public void Test_Fail(){
        String email = "@@@@@@@@gmail.com";

        //find the email address edit text and type in the email address
        onView(withId(R.id.editemail)).perform(typeText(email), closeSoftKeyboard());

        //click the reset button
        onView(withId(R.id.reset)).perform(click());

        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        //Check whether there the "An email has been sent to reset your password" message appears, if not the test is a fail
        //Expected Result: Fail
        resetpassword activity = mActivityTestRule.getActivity();
        onView(withText(R.string.reset_success)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }
}
