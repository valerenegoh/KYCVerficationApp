package com.example.asus.remotekyc;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by Li Yang on 13/3/2018.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class signupTest {
    @Rule
    public ActivityTestRule<RegisterKYC> mActivityTestRule = new ActivityTestRule<>(RegisterKYC.class);

    @Test //Success Case
    public void Test_success(){
        String name = "Jerry";
        String nric = "S8320356F";
        String email = "jerry4@gmail.com";
        String password = "Sutd@1234";
        String dob = "23/03/1994";

        onView(withId(R.id.name)).perform(typeText(name), closeSoftKeyboard());

        //find the nric edit text and type in nric
        onView(withId(R.id.nric)).perform(typeText(nric), closeSoftKeyboard());

        //find the email address edit text and type in the email address
        onView(withId(R.id.edemail)).perform(typeText(email), closeSoftKeyboard());

        //find the password edit text and type in the password
        onView(withId(R.id.edpassword)).perform(typeText(password), closeSoftKeyboard());

        //find the date of birth edit text and type in the date of birth
        onView(withId(R.id.eddob)).perform(typeText(dob), closeSoftKeyboard());

        //click the RegisterKYC button
        onView(withId(R.id.signup)).perform(click());

        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        //Check whether there the "Creating Acccount.." message appears, if not the test is a fail
        RegisterKYC activity = mActivityTestRule.getActivity();
        onView(withText(R.string.success_toast)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(3000);
    }

    @Test //Failure Case: Wrong Email Format
    public void Test_checkemail(){
        String name = "Allen";
        String nric = "S9560789R";
        String email = "allengmail.com";
        String password = "Sutd@1234";
        String dob = "23/06/1993";

        onView(withId(R.id.name)).perform(typeText(name), closeSoftKeyboard());

        //find the nric edit text and type in nric
        onView(withId(R.id.nric)).perform(typeText(nric), closeSoftKeyboard());

        //find the email address edit text and type in the email address
        onView(withId(R.id.edemail)).perform(typeText(email), closeSoftKeyboard());

        //find the password edit text and type in the password
        onView(withId(R.id.edpassword)).perform(typeText(password), closeSoftKeyboard());

        //find the date of birth edit text and type in the date of birth
        onView(withId(R.id.eddob)).perform(typeText(dob), closeSoftKeyboard());

        //click the RegisterKYC button
        onView(withId(R.id.signup)).perform(click());

        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        //Check whether there the "Creating Acccount.." message appears, if not the test is a fail
        RegisterKYC activity = mActivityTestRule.getActivity();
        onView(withText(R.string.success_toast)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test //Failure Case: Wrong Password Format
    public void Test_passcheck(){
        String name = "Tommy";
        String nric = "S8450643F";
        String email = "tommy4@gmail.com";
        String password = "sutd1234";
        String dob = "23/04/1993";

        onView(withId(R.id.name)).perform(typeText(name), closeSoftKeyboard());

        //find the nric edit text and type in nric
        onView(withId(R.id.nric)).perform(typeText(nric), closeSoftKeyboard());

        //find the email address edit text and type in the email address
        onView(withId(R.id.edemail)).perform(typeText(email), closeSoftKeyboard());

        //find the password edit text and type in the password
        onView(withId(R.id.edpassword)).perform(typeText(password), closeSoftKeyboard());

        //find the date of birth edit text and type in the date of birth
        onView(withId(R.id.eddob)).perform(typeText(dob), closeSoftKeyboard());

        //click the RegisterKYC button
        onView(withId(R.id.signup)).perform(click());

        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        //Check whether there the "Creating Acccount.." message appears, if not the test is a fail
        RegisterKYC activity = mActivityTestRule.getActivity();
        onView(withText(R.string.success_toast)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test //Failure Case: Wrong Date of Birth Format
    public void Test_dobcheck(){
        String name = "Sammy";
        String nric = "S8510244F";
        String email = "sammy4@gmail.com";
        String password = "Sutd@1234";
        String dob = "23/04/199";

        onView(withId(R.id.name)).perform(typeText(name), closeSoftKeyboard());

        //find the nric edit text and type in nric
        onView(withId(R.id.nric)).perform(typeText(nric), closeSoftKeyboard());

        //find the email address edit text and type in the email address
        onView(withId(R.id.edemail)).perform(typeText(email), closeSoftKeyboard());

        //find the password edit text and type in the password
        onView(withId(R.id.edpassword)).perform(typeText(password), closeSoftKeyboard());

        //find the date of birth edit text and type in the date of birth
        onView(withId(R.id.eddob)).perform(typeText(dob), closeSoftKeyboard());

        //click the RegisterKYC button
        onView(withId(R.id.signup)).perform(click());

        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        //Check whether there the "Creating Acccount.." message appears, if not the test is a fail
        RegisterKYC activity = mActivityTestRule.getActivity();
        onView(withText(R.string.success_toast)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test //Failure Case: Duplicate NRIC in Database
    public void Test_checkduplicate(){
        String name = "Merlin";
        String nric = "S9123456A";
        String email = "merlin4@gmail.com";
        String password = "Sutd@1234";
        String dob = "23/05/1993";

        onView(withId(R.id.name)).perform(typeText(name), closeSoftKeyboard());

        //find the nric edit text and type in nric
        onView(withId(R.id.nric)).perform(typeText(nric), closeSoftKeyboard());

        //find the email address edit text and type in the email address
        onView(withId(R.id.edemail)).perform(typeText(email), closeSoftKeyboard());

        //find the password edit text and type in the password
        onView(withId(R.id.edpassword)).perform(typeText(password), closeSoftKeyboard());

        //find the date of birth edit text and type in the date of birth
        onView(withId(R.id.eddob)).perform(typeText(dob), closeSoftKeyboard());

        //click the RegisterKYC button
        onView(withId(R.id.signup)).perform(click());

        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        //Check whether there the "Creating Acccount.." message appears, if not the test is a fail
        RegisterKYC activity = mActivityTestRule.getActivity();
        onView(withText(R.string.success_toast)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }



}


