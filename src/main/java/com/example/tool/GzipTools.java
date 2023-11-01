package com.example.tool;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/11/7
 * @description: gizp 压缩工具类
 */
@Slf4j
public class GzipTools {

    /**
     * 字符串压缩为 gzip 字节数组
     * 
     * @param content 内容
     * @return 字节数组
     */
    public static byte[] compress(String content) {
        return compress(content, StandardCharsets.UTF_8.name());
    }

    /**
     * 字符串压缩为 gzip 字节数组
     * 
     * @param content 内容
     * @param encoding 编码
     * @return 字节数组
     */
    private static byte[] compress(String content, String encoding) {
        if (content == null || content.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(content.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {
            log.error("gzip compress error: ", e);
        }
        return out.toByteArray();
    }

    /**
     * gzip 解压缩
     * 
     * @param bytes 字节数组
     * @return 字节数组
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            log.error("gzip compress error: ", e);
        }
        return out.toByteArray();
    }

    /**
     * 解压并返回 String
     * 
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, StandardCharsets.UTF_8.name());
    }

    /**
     * 解压并返回 字节数组
     * 
     * @param bytes 字节数组
     * @return 字节数组
     */
    public static byte[] uncompressToByteArray(byte[] bytes) {
        return uncompressToByteArray(bytes, StandardCharsets.UTF_8.name());
    }

    /**
     * 解压成字符串
     * 
     * @param bytes 压缩后的字节数组
     * @param encoding 编码方式
     * @return 解压后的字符串
     */
    private static String uncompressToString(byte[] bytes, String encoding) {
        byte[] result = uncompressToByteArray(bytes, encoding);
        return new String(result);
    }

    /**
     * 解压成 字节数组
     * 
     * @param bytes 字节数组
     * @param encoding 编码
     * @return 字节数组
     */
    private static byte[] uncompressToByteArray(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            log.error("gzip uncompress error: ", e);
            return null;
        }
    }

    /**
     * 将字节流转换成文件
     * 
     * @param filename 文件名
     * @param data 内容
     */
    public static void saveFile(String filename, byte[] data) {
        if (Objects.isNull(data)) {
            return;
        }
        try {
            String filepath = "/" + filename;
            File file = new File(filepath);
            if (file.exists()) {
                file.deleteOnExit();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data, 0, data.length);
            fos.flush();
            fos.close();
            System.out.println(file);
        } catch (IOException e) {
            log.error("save gzip file error: ", e);
        }
    }
}
