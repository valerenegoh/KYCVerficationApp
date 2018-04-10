package com.example.liyang.remotekyc;


import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.SecureRandom;
import java.util.Random;

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
 * Created by Li Yang on 10/4/2018.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CyptoTest {
    /*
    For this test, if a "Verified" Toast appears, verification is a success
    Success Case => Pass
    Failure Case => Fail
 */
    @Rule
    public ActivityTestRule<SecurityVerification> mActivityTestRule = new ActivityTestRule<>(SecurityVerification.class);

    @Test //Success Case: Correct Private Key => Success Message
    public void Test_success(){
        String pk = "MHsCAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQEEYTBfAgEBBBj/Q7kDI8G2yCiQPKUyoRa/zOzBELqpSCSgCgYIKoZIzj0DAQGhNAMyAARe0PM9xUSrEE6LQ4EhstMTH15b0ZYcB4NSKdA4XDFTqL5gNxjf" +
                "+iK9vPCgT9TfUoc=";

        onView(withId(R.id.editpk)).perform(typeText(pk), closeSoftKeyboard());

        //click the authenticate button
        onView(withId(R.id.authenticate)).perform(click());

        //Wait for 5s for the toast message to appear
        SystemClock.sleep(5000);
        SecurityVerification activity = mActivityTestRule.getActivity();
        onView(withText(R.string.crypto_success)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(5000);
    }

    @Test //Failure Case: Wrong Private Key => No Success Message
    public void Test_FailOne(){
        //Change from MH => HH in the first two words
        String pk = "HHsCAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQEEYTBfAgEBBBj/Q7kDI8G2yCiQPKUyoRa/zOzBELqpSCSgCgYIKoZIzj0DAQGhNAMyAARe0PM9xUSrEE6LQ4EhstMTH15b0ZYcB4NSKdA4XDFTqL5gNxjf" +
                "+iK9vPCgT9TfUoc=";

        onView(withId(R.id.editpk)).perform(typeText(pk), closeSoftKeyboard());

        //click the authenticate button
        onView(withId(R.id.authenticate)).perform(click());

        //Wait for 5s for the toast message to appear
        SystemClock.sleep(5000);
        SecurityVerification activity = mActivityTestRule.getActivity();
        onView(withText(R.string.crypto_success)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(5000);
    }

    @Test //Failure Case: Random Generated Private Key => No Success Message
    public void Test_FailTwo(){
        String Correct_pk = "MHsCAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQEEYTBfAgEBBBj/Q7kDI8G2yCiQPKUyoRa/zOzBELqpSCSgCgYIKoZIzj0DAQGhNAMyAARe0PM9xUSrEE6LQ4EhstMTH15b0ZYcB4NSKdA4XDFTqL5gNxjf" +
                "+iK9vPCgT9TfUoc=";
        RandomString pk = new RandomString(Correct_pk.length());

        onView(withId(R.id.editpk)).perform(typeText(pk.nextString()), closeSoftKeyboard());

        //click the authenticate button
        onView(withId(R.id.authenticate)).perform(click());

        //Wait for 5s for the toast message to appear
        SystemClock.sleep(5000);
        //Check whether there the "Invalid email or password." message appears, if not the test is a fail
        SecurityVerification activity = mActivityTestRule.getActivity();
        onView(withText(R.string.crypto_success)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
        SystemClock.sleep(5000);
    }

}

class RandomString
{

    /* Assign a string that contains the set of characters you allow. */
    private static final String symbols = "ABCDEFGJKLMNPRSTUVWXYZ0123456789";

    private final Random random = new SecureRandom();

    private final char[] buf;

    public RandomString(int length)
    {
        if (length < 1)
            throw new IllegalArgumentException("length < 1: " + length);
        buf = new char[length];
    }

    public String nextString()
    {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols.charAt(random.nextInt(symbols.length()));
        return new String(buf);
    }

}
