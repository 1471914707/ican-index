package com.ican.util;

import org.springframework.beans.factory.annotation.Value;

public class UrlUtil {

    @Value("${ican.url.admin}")
    public static String adminUrl;

    @Value("${ican.url.school}")
    public static String schoolUrl;

    @Value("${ican.url.college}")
    public static String collegeUrl;

    @Value("${ican.url.teacher}")
    public static String teacherUrl;

    @Value("${ican.url.student}")
    public static String studentUrl;

    @Value("${ican.url.bk}")
    public static String bkUrl;

}
