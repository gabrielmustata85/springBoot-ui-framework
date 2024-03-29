package com.ui.automation.framework.pagefactory.mobile.interceptor;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.ui.automation.framework.pagefactory.WithTimeoutProcessor;
import com.ui.automation.framework.pagefactory.mobile.ProxyUtil;
import com.ui.automation.framework.pagefactory.mobile.ThrowableUtil;
import com.ui.automation.framework.webdriver.Alert;
import com.ui.automation.framework.webdriver.Sleeper;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.WithTimeout;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static io.appium.java_client.pagefactory.utils.ProxyFactory.getEnhancedProxy;

/**
 * Intercepts requests to the list of {@link MobileElement}
 */
@Slf4j
public class ElementListInterceptor implements MethodInterceptor {
    /**
     * The Locator.
     */
    protected final ElementLocator locator;
    /**
     * The Driver.
     */
    protected final WebDriver driver;
    /**
     * The Field.
     */
    protected final Field field;
    /**
     * The Alert.
     */
    protected final Alert alert;
    private final Sleeper sleeper;

    private List<WebElement> proxyedElements = new ArrayList<>();

    /**
     * Instantiates a new Element list interceptor.
     *
     * @param locator the locator
     * @param driver  the driver
     * @param field   the field
     */
    public ElementListInterceptor(ElementLocator locator, WebDriver driver, Field field) {
        this.locator = locator;
        this.driver = driver;
        this.field = field;
        this.alert = new Alert(driver);
        this.sleeper = new Sleeper();
    }

    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return proxy.invokeSuper(obj, args);
        }

        List<WebElement> realElements = null;
        int timeout;
        if (field.isAnnotationPresent(WithTimeout.class)) {
            realElements = locator.findElements();
            WithTimeout withTimeout = field.getAnnotation(WithTimeout.class);
            int unit = 1;
            switch (withTimeout.unit()) {
                case SECONDS:
                    break;
                case HOURS:
                    unit = 3600;
                    break;
                case MINUTES:
                    unit = 60;
                    break;
            }
            timeout = (int) withTimeout.time() * unit;
        } else {
            timeout = WithTimeoutProcessor.getTimeOutOfFind(field, 2);
            long end = System.currentTimeMillis() + timeout;
            while (System.currentTimeMillis() < end) {
                realElements = locator.findElements();
                if (realElements != null && realElements.size() > 0) {
                    break;
                }
                this.sleeper.sleep(500);
            }

        }
        if (realElements == null) {
            log.error("the " + this.field.getName()
                    + " element can't be found and the time(" + String.valueOf(timeout) + ") is out");
            throw new RuntimeException("the " + this.field.getName()
                    + " element can't be found and the time(" + String.valueOf(timeout) + ") is out");
        }

        try {
            int index = 0;
            proxyedElements.clear();
            for (WebElement element : realElements) {
                index++;
                SubElementInterceptor elementInterceptor = new SubElementInterceptor(element, driver, field, index);
                proxyedElements.add((WebElement) getEnhancedProxy(ProxyUtil.getTypeForProxy(driver.getClass()), elementInterceptor));
            }
            return method.invoke(proxyedElements, args);
        } catch (Throwable t) {
            throw ThrowableUtil.extractReadableException(t);
        }
    }

}
