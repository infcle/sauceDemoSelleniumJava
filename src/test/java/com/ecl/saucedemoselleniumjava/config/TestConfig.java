package com.ecl.saucedemoselleniumjava.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Centralized test configuration based on environment variables and YAML.
 * Priority: environment variable > JVM -D property > test-config.yml.
 */
public final class TestConfig {

    private static final String YAML_FILE = "test-config.yml";
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{([^:}]+)(?::([^}]*))?}");
    private static final RootConfig CONFIG = loadYamlConfig();

    private TestConfig() {
    }

    public static String browser() {
        return get("BROWSER", "browser", CONFIG.browser);
    }

    public static String baseUrl() {
        return get("BASE_URL", "base.url", CONFIG.base.url);
    }

    public static String username() {
        return get("USER_STANDARD", "user.standard", CONFIG.user.standard);
    }

    public static String password() {
        return get("USER_PASSWORD", "user.password", CONFIG.user.password);
    }

    public static String checkoutFirstName() {
        return get("CHECKOUT_FIRST_NAME", "checkout.firstName", CONFIG.checkout.firstName);
    }

    public static String checkoutLastName() {
        return get("CHECKOUT_LAST_NAME", "checkout.lastName", CONFIG.checkout.lastName);
    }

    public static String checkoutZipCode() {
        return get("CHECKOUT_ZIP_CODE", "checkout.zipCode", CONFIG.checkout.zipCode);
    }

    private static String get(String envKey, String key, String yamlValue) {
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue.trim();
        }

        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }

        if (yamlValue != null && !yamlValue.isBlank()) {
            return yamlValue.trim();
        }

        throw new IllegalStateException(
                "Missing config for key '" + key + "'. " +
                        "Set it in " + YAML_FILE + ", via -D" + key + ", or env var " + envKey + "."
        );
    }

    private static RootConfig loadYamlConfig() {
        try (InputStream inputStream = TestConfig.class.getClassLoader().getResourceAsStream(YAML_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Missing required config file: " + YAML_FILE);
            }

            String rawYaml = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String resolvedYaml = resolvePlaceholders(rawYaml);

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            RootConfig config = mapper.readValue(resolvedYaml, RootConfig.class);
            config.validate();
            return config;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load YAML config from " + YAML_FILE, e);
        }
    }

    private static String resolvePlaceholders(String text) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(text);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String variableName = matcher.group(1);
            String defaultValue = matcher.group(2);

            String replacement = System.getenv(variableName);
            if (replacement == null || replacement.isBlank()) {
                replacement = System.getProperty(variableName);
            }
            if ((replacement == null || replacement.isBlank()) && defaultValue != null) {
                replacement = defaultValue;
            }
            if (replacement == null) {
                replacement = "";
            }

            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class RootConfig {
        public String browser;
        public BaseConfig base;
        public UserConfig user;
        public CheckoutConfig checkout;

        void validate() {
            if (base == null) {
                throw new IllegalStateException("Missing 'base' section in " + YAML_FILE);
            }
            if (user == null) {
                throw new IllegalStateException("Missing 'user' section in " + YAML_FILE);
            }
            if (checkout == null) {
                throw new IllegalStateException("Missing 'checkout' section in " + YAML_FILE);
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class BaseConfig {
        public String url;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class UserConfig {
        public String standard;
        public String password;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CheckoutConfig {
        public String firstName;
        public String lastName;
        public String zipCode;
    }
}
