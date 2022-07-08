package com.ui.automation.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    public static final ExtentReports extentReports = new ExtentReports();
    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./extent-reports/extent-report.html");
        reporter.config().setReportName("Sample Extent Report");
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setTimelineEnabled(true);
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Site Name", "Framework");
        extentReports.setSystemInfo("QA", "Gabriel Mustata");
        return extentReports;
    }
}
