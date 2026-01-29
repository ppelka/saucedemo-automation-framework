package com.ppelka.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * TestNG annotation transformer that applies the RetryAnalyzer
 * to every test method automatically.
 *
 * This ensures consistent retry behavior across the entire test suite
 * without requiring manual configuration on each @Test annotation.
 */
public class RetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {

        // Apply RetryAnalyzer to all test methods
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
