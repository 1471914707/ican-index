package com.ican.util;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static void writIn(InputStream in, String fileName) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
        byte[] buffer = new byte[4096];
        boolean var4 = false;

        int count;
        while((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }

        out.close();
        in.close();
    }
}
