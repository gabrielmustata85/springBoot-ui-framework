package com.ui.automation.pages;

import com.ui.automation.framework.Inject.annotation.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Value;

@Page
public class SearchPage{

    private WebDriver driver;

    @Value("${webPath}")
    private String url;

    @FindBy(id = "kw")
    private WebElement searchTestBox;

    @FindBy(id = "su")
    private WebElement searchBtn;

    public void searchFor(String text) {
        driver.get(url);
        searchTestBox.sendKeys(text);
        searchBtn.click();
    }
}
