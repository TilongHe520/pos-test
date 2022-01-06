package com.alone.main;

import com.alone.test.PosAutoTest;
import org.testng.TestNG;

/**
 * @Author: hetilong
 * @Date: 2022/1/5 17:29
 */
public class PosAutomationRun {

    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        Class[] classes = {PosAutoTest.class};
        testNG.setTestClasses(classes);
        testNG.run();
    }
}
