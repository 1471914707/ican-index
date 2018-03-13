package com.ican.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;
import java.util.UUID;

public class IcanUtil {
    private static final Logger logger = LoggerFactory.getLogger(IcanUtil.class);

    public static String[] IMAGE_FILE_EXTD = new String[]{"png", "bmp", "jpg", "jpeg"};

    public static final String COOKIE_NAME = "ican_ticket";

    //角色定义
    public static int ADMIN_SUPER = 1;
    public static int ADMIN_SYSTEM = 2;
    public static int USER_SCHOOL = 3;
    public static int USER_COLLEGE = 4;
    public static int USER_TEACHER = 5;
    public static int USER_STUDENT = 6;

    //角色数量定义
    public static int USER_MIN = 1;
    public static int USER_MAX = 6;

    //角色状态定义
    public static int USER_STATUS_INIT = 0;
    public static int USER_STATUS_VALID = 1;
    public static int USER_STATUS_INVALID = 2;

    //跟进类型定义
    public static int FOLLOW_TYPE_SCHOOL = 1;
    public static int FOLLOW_TYPE_APPEAL = 2;



    public static String getNewCookieValue() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static boolean isFileAllowed(String fileName) {
        for (String ext : IMAGE_FILE_EXTD) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBank(String str) {
        if (str == null || str.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }
}
