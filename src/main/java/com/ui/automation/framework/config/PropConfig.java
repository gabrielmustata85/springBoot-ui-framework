package com.ui.automation.framework.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Prop config.
 */
@Component
@Getter
@Setter
public class PropConfig implements InitializingBean {

    private static PropConfig instance;

    //proxy config
    @Value("${localhost:127.0.0.1}")
    private String localhost;
    @Value("${localport:8888}")
    private String localport;
    @Value("${timeout:30000}")
    private String timeout = "";

    //server config
    @Value("${webPath}")
    private String webPath;
    @Value("${dbPoolName:}")
    private String dbPoolName;

    //retry
    @Value("${retryCount:1}")
    private int retryCount;

    //custom report
    @Value("${sourceCodeEncoding:UTF-8}")
    private String sourceCodeEncoding;
    @Value("${sourceCodeDir:src}")
    private String sourceCodeDir;

    //browser config
    @Value("${coreType:GOOGLECHROME}")
    private String coreType;
    @Value("${htmlUnitEmulationType:}")
    private String htmlUnitEmulationType;
    @Value("${htmlUnitProxy:}")
    private String htmlUnitProxy;

    //base package name
    @Value("${basePackage:}")
    private String basePackage;

    //Android
    @Value("${appBin:}")
    private String appBin;
    @Value("${appiumServerUrl:}")
    private String appiumServerUrl;
    @Value("${deviceName:}")
    private String deviceName;

    //OCR
    @Value("${tessPath:}")
    private String tessPath;

    //Highlight element for android
    @Value("${highlight:false}")
    private boolean highlight;

    //Load chrome extensions or not
    @Value("${debug:false}")
    private boolean debug;

    //ios
    @Value("${platformVersion:}")
    private String platformVersion;
    @Value("${uuid:}")
    private String uuid;

    //singleton test
    @Value("${noReset:false}")
    private boolean noReset;

    public static PropConfig get() {
        return instance;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
    }

}
