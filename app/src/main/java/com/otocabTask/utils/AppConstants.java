package com.otocabTask.utils;

/**
 * Created by aya mohamed on 11/12/19.
 */


public final class AppConstants {

    public static final String PREF_NAME = "app_prefs";
    public static final String STRING_NULL_INDEX = "null";
    public static final boolean BOOLEAN_NULL_INDEX = false;
    public static final String DEFAULT_IMAGE = "https://i.ibb.co/5nPSYgf/index.png";
    public static final String PWD_PATTERN = "().{8,}";
    public static final String PHONE_PATTERN = "\\d{11}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
    public static final String ERROR_MESSAGE = "error_message";
    public static final String SUCCESS_MESSAGE = "success_message";
    public static final long LONG_NULL_INDEX = 0L;
    public static final int REQUEST_CODE_CHOOSE = 101;
    public static final Double DOUBLE_DEFAULT_INDEX = 0.0;
    public static final int INTEGER_DEFAULT_INDEX = -1;
    public static final String AR = "ar";
    public static final String EN = "en";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String BACK = "back";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}