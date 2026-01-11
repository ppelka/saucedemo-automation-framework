package com.ppelka.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Lightweight configuration loader.
 *
 * Resolution order:
 *  1. System properties (highest priority)
 *  2. config.properties (if present on classpath)
 *  3. Provided default value
 *
 * Recommended keys:
 *  - browser   -> "chrome"
 *  - headless  -> "false"
 *  - base.url  -> "https://www.saucedemo.com/"
 */
public final class ConfigReader {

    private static final Properties props = new Properties();

    static {
        // Attempt to load config.properties, but do not fail if missing
        try (InputStream is = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (is != null) {
                props.load(is);
            } else {
                System.out.println("Info: config.properties not found; using system properties and defaults.");
            }

        } catch (IOException e) {
            // Do not interrupt test execution; fallback to defaults
            System.err.println("Warning: Failed to load config.properties: " + e.getMessage());
        }
    }

    private ConfigReader() {
        // Utility class; prevent instantiation
    }

    /**
     * Retrieves a configuration value.
     * Resolution order: System property -> properties file -> defaultValue.
     *
     * @param key          configuration key
     * @param defaultValue fallback value if key is not found
     * @return trimmed configuration value (never null)
     */
    public static String get(String key, String defaultValue) {
        // 1) System property
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys.trim();
        }

        // 2) Properties file
        String prop = props.getProperty(key);
        if (prop != null && !prop.isBlank()) {
            return prop.trim();
        }

        // 3) Default value
        return defaultValue;
    }

    /**
     * Retrieves a configuration value without a default.
     * Returns null if the key is not found.
     *
     * Use only when distinguishing between "missing" and "defaulted" values.
     */
    public static String get(String key) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys.trim();
        }

        String prop = props.getProperty(key);
        return (prop == null) ? null : prop.trim();
    }

    /**
     * Retrieves a boolean configuration value.
     * Resolution order: System property -> properties file -> defaultValue.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String val = get(key, Boolean.toString(defaultValue));
        return Boolean.parseBoolean(val);
    }

    /**
     * Retrieves an integer configuration value.
     * Resolution order: System property -> properties file -> defaultValue.
     * Falls back to defaultValue if parsing fails.
     */
    public static int getInt(String key, int defaultValue) {
        String val = get(key, Integer.toString(defaultValue));
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            System.err.println("Warning: config key '" + key + "' is not a valid int: '" + val + "'. Using default: " + defaultValue);
            return defaultValue;
        }
    }
}
