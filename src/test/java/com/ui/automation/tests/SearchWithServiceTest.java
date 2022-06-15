package com.ui.automation.tests;

import com.ui.automation.data.TestCaseData;
import com.ui.automation.framework.TestCaseBase;
import com.ui.automation.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

@Component
public class SearchWithServiceTest extends TestCaseBase {

    @Autowired
    SearchService searchService;

    @Test(dataProviderClass = TestCaseData.class, dataProvider = "searchData", description = "Search Spring Boot")
    public void googleSearch(String content) {
        searchService.search(content);
    }

    @Test(dataProviderClass = TestCaseData.class, dataProvider = "searchData", description = "Search Spring Boot 1")
    public void googleSearch1(String content) {
        searchService.search(content);
    }
}
