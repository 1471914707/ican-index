package com.ican.util;

public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";
    private static String USER_ID_KEY = "USER_ID_KEY";
    private static String SCHOOL_DAY_LOGIN = "SCHOOL_DAY_LOGIN";
    private static String EMAIL_VER = "EMAIL_VER";
    private static String KEYT_TEACHER = "KEYT_TEACHER";

    public static String getEmailVer(String email) {
        return EMAIL_VER + SPLIT + email;
    }

    public static String getKeytTeacher(int id) {
        return KEYT_TEACHER + SPLIT + id;
    }

    public static String getEventQueueKey() {
        return BIZ_EVENT;
    }

    public static String getSchoolDayLogin() {
        return SCHOOL_DAY_LOGIN;
    }

    public static String getUserIdKey() {
        return USER_ID_KEY;
    }

    public static String getLikeKey(int entityId, int entityType) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityId, int entityType) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
}
