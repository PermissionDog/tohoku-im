package com.github.permissiondog.tohokuim;

import java.time.format.DateTimeFormatter;

public class Constant {

    public static final String DATA_PATH = "data/";
    public static final String CONFIG_FILE = "config.json";
    public static final String PROFILE_FILE = DATA_PATH + "profile.json";
    public static final String FRIENDS_FILE = DATA_PATH + "friends.json";
    public static final String MESSAGES_FILE = DATA_PATH + "messages.json";

    public static final String DEFAULT_FILE_PATH = "/com/github/permissiondog/tohokuim/data/";
    public static final String DEFAULT_CONFIG = DEFAULT_FILE_PATH + "config.json";
    public static final String DEFAULT_PROFILE = DEFAULT_FILE_PATH + "profile.json";
    public static final String DEFAULT_FRIENDS = DEFAULT_FILE_PATH + "friends.json";
    public static final String DEFAULT_MESSAGES = DEFAULT_FILE_PATH + "messages.json";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
}
