package com.ui.automation.data;

import org.testng.annotations.DataProvider;

public class TestCaseData {

    @DataProvider(name = "searchData")
    public static Object[][] searchData() {
        return new Object[][]{
                {"Spring Boot"}
        };
    }
}
