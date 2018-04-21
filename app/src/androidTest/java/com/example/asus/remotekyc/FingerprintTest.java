package com.example.asus.remotekyc;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

public class FingerprintTest {

    private AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        //set the platform version
        caps.setCapability("deviceName", "Android emulator");
        caps.setCapability(CapabilityType.BROWSER_NAME, "6.0.1");       //mobile device's OS version
        caps.setCapability(CapabilityType.BROWSER_NAME, "Android");
        //set the emulator
        caps.setCapability("platformName", "Android");
        //set the package
        caps.setCapability("appPackage", "com.example.remotekyc");
        caps.setCapability("appActivity", "com.example.remotekyc.SomeHomePage");
        //set the file path
        String sFilepath = "build/outputs/apk/app-debug.apk"; // in IDE
        String filePath = new File(sFilepath).getAbsolutePath();
        caps.setCapability("app", filePath);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @Test
    public void Test_success() throws Exception {
        driver.findElement(By.id("fingerprint")).click();
        // runs on appium server where adb is installed, emulator must be attached to this node
        Runtime.getRuntime().exec("adb -e emu finger touch 123abc");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(@text,'successfully')]")).isDisplayed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}