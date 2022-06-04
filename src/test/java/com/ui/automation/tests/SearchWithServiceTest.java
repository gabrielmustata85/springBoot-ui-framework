package com.ui.automation.tests;

import com.ui.automation.framework.TestCaseBase;
import com.ui.automation.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

@Component
public class SearchWithServiceTest extends TestCaseBase {

    @Autowired
    SearchService searchService;

    @Test()
    public void pageFactoryTest(String content) {
        searchService.search(content);
    }
}
