package com.zhubo.fm.bll.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * 上传用户头像
 * Created by andy_lv on 2014/9/20.
 */
public class UploadPhoto {

    /**
     *
     * @param urlStr
     * @param map
     * @param bytes
     */
    public static String postFile(String urlStr, HashMap<String, String> map,
                                 byte[] bytes) {
        String result = "";
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod(urlStr);
        postMethod.setRequestHeader("X-Token",
                "f05da37f8656c78db7efdb64a1166fb6273caf0d");
        postMethod.setRequestHeader("X-Station", "1");
        postMethod.setRequestHeader("Content-Type", "binary");

        InputStreamRequestEntity re = new InputStreamRequestEntity(
                new ByteArrayInputStream(bytes));
        postMethod.setRequestEntity(re);

        System.out.println(urlStr);
        try {
            httpclient.executeMethod(postMethod);
            InputStream is = postMethod.getResponseBodyAsStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public  static String delete(String urlStr, HashMap<String, String> map) {
        String reuslt = "";
        HttpClient httpclient = new HttpClient();
        DeleteMethod postMethod = new DeleteMethod(urlStr);
        System.out.println(urlStr);
        Set<String> keySet = map.keySet();
        NameValuePair[] nameValuePairs = new NameValuePair[map.size()];
        int i = 0;
        for (String key : keySet) {
            nameValuePairs[i] = new NameValuePair(key, map.get(key));
            i++;
        }
        postMethod.setQueryString(nameValuePairs);
        try {
            httpclient.executeMethod(postMethod);
            InputStream is = postMethod.getResponseBodyAsStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reuslt = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reuslt;
    }
}
