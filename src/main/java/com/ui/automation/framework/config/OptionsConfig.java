package com.ui.automation.framework.config;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OptionsConfig implements InitializingBean {

    private static OptionsConfig instance;

    @Value("${windowSize}")
    private static String windowSize;

    @Value("${noSandbox}")
    private static String noSandbox;

    @Value("${startMaximize}")
    private static String startMaximize;

    @Value("${ignoreCertificateErrors}")
    private static String ignoreCertificateErrors;

    @Value("${disableInfoBars}")
    private static String disableInfoBars;

    @Value("${disableDevShmUsage}")
    private static String disableDevShmUsage;

    @Value("${disableBrowserSideNavigation}")
    private static String disableBrowserSideNavigation;

    @Value("${disableGpu}")
    private static String disableGpu;

    public static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(windowSize);
        chromeOptions.addArguments(noSandbox);
        chromeOptions.addArguments(startMaximize);
        chromeOptions.addArguments(ignoreCertificateErrors);
        chromeOptions.addArguments(disableInfoBars);
        chromeOptions.addArguments(disableDevShmUsage);
        chromeOptions.addArguments(disableBrowserSideNavigation);
        chromeOptions.addArguments(disableGpu);

        return chromeOptions;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
    }
}
