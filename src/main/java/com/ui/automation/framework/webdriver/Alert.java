package com.ui.automation.framework.webdriver;

import com.ui.automation.framework.helpers.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Alert.
 */
@Slf4j
public class Alert {

    /**
     * The Driver.
     */
    public WebDriver driver;
    private Sleeper sleeper = new Sleeper();

    /**
     * Instantiates a new Alert.
     *
     * @param driver the driver
     */
    public Alert(WebDriver driver) {
        this.driver = driver;
    }


    /**
     * Accept alert.
     */
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    /**
     * Accept alert and msg string.
     *
     * @return the string
     */
    public String acceptAlertAndMsg() {
        long current = System.currentTimeMillis();
        long endCur = current + 2000;
        String msg = null;
        while ((msg == null || msg.trim().equalsIgnoreCase("")) && current <= endCur) {
            try {
                org.openqa.selenium.Alert alert = driver.switchTo().alert();
                msg = alert.getText();
                alert.accept();
            } catch (NoAlertPresentException ignored) {
            }
            sleeper.sleep();
            current = System.currentTimeMillis();
        }
        return msg;
    }

    /**
     * Accept alert list.
     *
     * @param timeout the timeout
     * @return the list
     */
    public List<String> acceptAlert(long timeout) {
        List<String> alertMsg = new ArrayList<String>();
        long currentTime = System.currentTimeMillis();
        long maxTime = currentTime + timeout;
        while (currentTime < maxTime) {
            String msg = acceptAlertAndMsg();
            if (StringHelper.isNotEmpty(msg)) {
                alertMsg.add(msg);
                log.info(">>>>>>>>>>>>>>>>>>>>  alert:" + msg + " <<<<<<<<<<<<<<<<<<<<<<<<<<");
            }
            currentTime = System.currentTimeMillis();
        }
        return alertMsg;
    }


}
