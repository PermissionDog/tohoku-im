package com.github.permissiondog.tohokuim.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件相关工具类
 * @author PermissionDog
 */
public class FileUtil {
    /**
     * 读取文件
     *
     * @param fileName 文件名
     * @return 读入文本字符串
     */
    public static String readFile(String fileName) {
        try {
            return Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            System.out.println("读取 " + fileName + " 时出错");
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }


    /**
     * 写入文件
     *
     * @param fileName 文件名
     * @param fileData 要写入的文本数据
     */
    public static void writeFile(String fileName, String fileData) {
        try {
            Files.writeString(Paths.get(fileName), fileData);
        } catch (IOException e) {
            System.out.println("写到 " + fileName + " 时出错, 数据");
            System.out.println(fileData);
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 将 jar 包内资源文件写出
     *
     * @param resource	资源在 jar 包中的路径, 如 /com/github/permissiondog/community/resources/config.json
     * @param fileName	要写出的文件名
     */
    public static void writeResource(String resource, String fileName) {
        // 如何读取 jar 包内资源文件
        // https://stackoverflow.com/a/29747012/14936035
        try (InputStream in = FileUtil.class.getResourceAsStream(resource)) {
            try (OutputStream out = Files.newOutputStream(Paths.get(fileName))) {
                assert in != null;
                IOUtil.copy(in, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

