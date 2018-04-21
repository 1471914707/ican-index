package com.ican.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class IcanUtil {
    private static final Logger logger = LoggerFactory.getLogger(IcanUtil.class);

    public static String[] IMAGE_FILE_EXTD = new String[]{"png", "bmp", "jpg", "jpeg"};

    public static final String COOKIE_NAME = "ican_ticket";

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

    //获取随机六位验证码
    public static String getRandom() {
        String num = "";
        for (int i = 0 ; i < 6 ; i ++) {
            num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
        }
        return num;
    }

    //随机获取数字和字母组成特定长度的字符串
    public static String getStrNumRandom(Integer length) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                int choice = random.nextBoolean() ? 65 : 97; //取得65大写字母还是97小写字母
                str.append((char) (choice + random.nextInt(26)));// 取得大写字母
            } else { // 数字
                str.append(random.nextInt(10));
            }
        }
        return str.toString();
    }
}
