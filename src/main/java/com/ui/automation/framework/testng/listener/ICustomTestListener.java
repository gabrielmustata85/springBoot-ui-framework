package com.ui.automation.framework.testng.listener;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * The interface Custom test listener.
 */
public interface ICustomTestListener {

    /**
     * On test failure.
     *
     * @param iTestResult the iTestResult
     */
    void onTestFailure(ITestResult iTestResult);

    /**
     * On test skipped.
     *
     * @param iTestResult the iTestResult
     */
    void onTestSkipped(ITestResult iTestResult);

    /**
     * On test success.
     *
     * @param iTestResult the iTestResult
     */
    void onTestSuccess(ITestResult iTestResult);

    /**
     * On test start.
     *
     * @param iTestResult the iTestResult
     */
    void onTestStart(ITestResult iTestResult);

    /**
     * On start.
     *
     * @param testContext the test context
     */
    void onStart(ITestContext testContext);

    void onStart(ISuite iSuite);

    /**
     * On finish.
     *
     * @param iSuite the test context
     */
    void onFinish(ISuite iSuite);

    void onFinish(ITestContext testContext);
}
