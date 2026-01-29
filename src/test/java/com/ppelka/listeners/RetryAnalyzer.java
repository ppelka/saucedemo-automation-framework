package com.ppelka.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * TestNG retry mechanism.
 *
 * Retries a failed test once (configurable via maxRetryCount).
 * Useful for handling occasional flakiness caused by timing issues,
 * network delays, or transient UI states.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;

    /**
     * Maximum number of retries for a failed test.
     * Default: 1 retry.
     */
    private static final int maxRetryCount = 1;

    /**
     * Determines whether a failed test should be retried.
     *
     * @param result the result of the test execution
     * @return true if the test should be retried, false otherwise
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }
}
