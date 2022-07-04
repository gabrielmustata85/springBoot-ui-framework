package com.ui.automation.framework.testng.listener;

import com.aventstack.extentreports.Status;
import com.oracle.tools.packager.Log;
import com.ui.automation.framework.extentreport.ExtentManager;
import com.ui.automation.framework.extentreport.ExtentTestManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.Objects;

public class ITestListener implements ICustomTestListener{

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getTestName();
    }

    private Object[] getParameters(ITestResult iTestResult) {
        return iTestResult.getParameters();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        Log.info("I am in onStart method " + iTestContext.getName());
//        iTestContext.setAttribute("WebDriver", driver);
    }

    @Override
    public void onStart(ISuite iSuite) {

    }

    @Override
    public void onFinish(ISuite iSuite) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        Log.info("I am in onFinish method " + iTestContext.getName());
        ExtentManager.extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is succeed.");
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Throwable t = new Exception();
        Log.info(getTestMethodName(iTestResult) + " test is failed.");
        ITestContext context = iTestResult.getTestContext();
        WebDriver driver = (WebDriver) context.getAttribute("driver");
        String base64Screenshot =
                "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);

        ExtentTestManager.getTest().log(Status.FAIL, "Test Failed " + t,
                ExtentTestManager.getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) + " test is skipped.");
        //ExtentReports log operation for skipped tests.
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
    }

}
