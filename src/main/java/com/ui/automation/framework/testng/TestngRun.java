package com.ui.automation.framework.testng;


import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.internal.Parser;
import java.io.IOException;
import java.util.List;

/**
 * testng run for spring boot
 */
public class TestngRun {

    /**
     * run testng suite xml
     *
     * @param xmlSuiteFile  the testng suite xml file
     */
    public static void execute(String xmlSuiteFile) {
        try {
            List<XmlSuite> xmlSuiteList = (List <XmlSuite>)(new Parser(xmlSuiteFile).parse());
            TestNG testNG = new TestNG();
            testNG.setXmlSuites(xmlSuiteList);
            testNG.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
