package com.ui.automation.services;

import com.ui.automation.pages.SearchPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    SearchPage searchPage;

    public void search(String content){
        searchPage.searchFor(content);
//        searchPage.verifyResult();
    }
}
