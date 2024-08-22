package org.example.dienluc.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    // Regex for phone numbers (Vietnam)
    public static final String PHONE_REGEX = "^(0[3|5|7|8|9])+([0-9]{8})$";

    // Regex for email
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    // Regex for citizen identification number (CCCD)
    public static final String CCCD_REGEX = "^(0)([0-9]{11})$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "vi";

    private Constants() {}
}
