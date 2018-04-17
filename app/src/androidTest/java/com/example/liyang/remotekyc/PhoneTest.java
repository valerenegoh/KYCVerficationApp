package com.example.liyang.remotekyc;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.liyang.remotekyc.SecondStep.SmsOtpVerfication;

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

/**
 * Created by Li Yang on 2/4/2018.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class PhoneTest {
    /*
    For this test, a toast will only appear if there is an error
    Failure Case => Pass
    Success Case => Failed
 */
    @Rule
    public ActivityTestRule<SmsOtpVerfication> mActivityTestRule = new ActivityTestRule<>(SmsOtpVerfication.class);

    @Test //Success Case
    public void Test_success() {
        String phone_number = "+6591342356";
        onView(withId(R.id.phoneverify)).perform(typeText(phone_number), closeSoftKeyboard());
        //click the Send OTP button
        onView(withId(R.id.sendotp)).perform(click());
        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        SmsOtpVerfication activity = mActivityTestRule.getActivity();
        onView(withText(R.string.phone_fail)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(2000);
        //Will not receive the toast "Invalid Phone Number".
        //Expected Result: Failure
        onView(withText(R.string.phone_invalid)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test //Failure Case: Without Country Code
    public void Test_FailOne() {
        String phone_number = "91342356";
        onView(withId(R.id.phoneverify)).perform(typeText(phone_number), closeSoftKeyboard());
        //click the Send OTP button
        onView(withId(R.id.sendotp)).perform(click());
        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        SmsOtpVerfication activity = mActivityTestRule.getActivity();
        onView(withText(R.string.phone_fail)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(2000);
        //Will receive the toast "Invalid Phone Number".
        //Expected Result: Success
        onView(withText(R.string.phone_invalid)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test //Failure Case: With Country Code and no eight numbers
    public void Test_FailTwo() {
        String phone_number = "+659134";
        onView(withId(R.id.phoneverify)).perform(typeText(phone_number), closeSoftKeyboard());
        //click the Send OTP button
        onView(withId(R.id.sendotp)).perform(click());
        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        SmsOtpVerfication activity = mActivityTestRule.getActivity();
        onView(withText(R.string.phone_fail)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(2000);
        //Will receive the toast "Invalid Phone Number".
        //Expected Result: Success
        onView(withText(R.string.phone_invalid)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test //Failure Case: No eight numbers
    public void Test_FailThree() {
        String phone_number = "+659134";
        onView(withId(R.id.phoneverify)).perform(typeText(phone_number), closeSoftKeyboard());
        //click the Send OTP button
        onView(withId(R.id.sendotp)).perform(click());
        //Wait for 2s for the toast message to appear
        SystemClock.sleep(2000);
        SmsOtpVerfication activity = mActivityTestRule.getActivity();
        onView(withText(R.string.phone_fail)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(2000);
        //Will receive the toast "Invalid Phone Number".
        //Expected Result: Success
        onView(withText(R.string.phone_invalid)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

}
