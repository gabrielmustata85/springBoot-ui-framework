package com.ui.automation.framework.config;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Prop config.
 */
@Component
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

    public String getLocalhost() {
        return localhost;
    }

    public void setLocalhost(String localhost) {
        this.localhost = localhost;
    }

    public String getLocalport() {
        return localport;
    }

    public void setLocalport(String localport) {
        this.localport = localport;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    public String getDbPoolName() {
        return dbPoolName;
    }

    public void setDbPoolName(String dbPoolName) {
        this.dbPoolName = dbPoolName;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getSourceCodeEncoding() {
        return sourceCodeEncoding;
    }

    public void setSourceCodeEncoding(String sourceCodeEncoding) {
        this.sourceCodeEncoding = sourceCodeEncoding;
    }

    public String getSourceCodeDir() {
        return sourceCodeDir;
    }

    public void setSourceCodeDir(String sourceCodeDir) {
        this.sourceCodeDir = sourceCodeDir;
    }

    public String getCoreType() {
        return coreType;
    }

    public void setCoreType(String coreType) {
        this.coreType = coreType;
    }

    public String getHtmlUnitEmulationType() {
        return htmlUnitEmulationType;
    }

    public void setHtmlUnitEmulationType(String htmlUnitEmulationType) {
        this.htmlUnitEmulationType = htmlUnitEmulationType;
    }

    public String getHtmlUnitProxy() {
        return htmlUnitProxy;
    }

    public void setHtmlUnitProxy(String htmlUnitProxy) {
        this.htmlUnitProxy = htmlUnitProxy;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getAppBin() {
        return appBin;
    }

    public void setAppBin(String appBin) {
        this.appBin = appBin;
    }

    public String getAppiumServerUrl() {
        return appiumServerUrl;
    }

    public void setAppiumServerUrl(String appiumServerUrl) {
        this.appiumServerUrl = appiumServerUrl;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTessPath() {
        return tessPath;
    }

    public void setTessPath(String tessPath) {
        this.tessPath = tessPath;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getPlatFormVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public boolean isNoReset() {
        return noReset;
    }

    public void setNoReset(boolean noReset) {
        this.noReset = noReset;
    }


}
