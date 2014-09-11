package com.andy.commonlibrary.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.zip.GZIPInputStream;

/**
 * http请求基础类
 * Created by andy_lv on 2014/8/23.
 */
public class BaseRequest {

    private final String TAG = "BaseRequest" ;
    //从连接池建立创建任务的超时时间
    private static final int POOL_CONNECT_TIME = 1000;
    //本地与服务器端建立socket连接的超时时间
    private static final int SERVER_CONNECT_TIME = 2000;
    //本地与服务器端socket读取，写入的超时时间
    private static final int READ_WRITE_TIME = 4000;

    public  DefaultHttpClient client;

    /**
     * 构造器
     */
    public BaseRequest(){
        init();
    }

    /**
     * 初始化http请求参数
     */
    private void init(){
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        // 超时设置
		/* 从连接池中取连接的超时时间 */
        ConnManagerParams.setTimeout(params, POOL_CONNECT_TIME);
		/* 连接超时 */
        HttpConnectionParams.setConnectionTimeout(params, SERVER_CONNECT_TIME);
		/* 请求超时 */
        HttpConnectionParams.setSoTimeout(params, READ_WRITE_TIME);

        // 设置我们的HttpClient支持HTTP和HTTPS两种模式
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        // 使用线程安全的连接管理来创建HttpClient
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
        client = new DefaultHttpClient(conMgr, params);
    }

    /**
     * 处理Get 请求
     * @param url    请求url
     * @return       服务器端返回的报文
     * @throws IOException
     * @throws HttpException
     */
    public String getRequest(List<NameValuePair> params,String url) throws IOException, HttpException,TimeoutException{
        String result = null;
        if(null != params){
           String str = "";
          Iterator<NameValuePair> iterator =  params.iterator();
          while(iterator.hasNext()){
              NameValuePair nameValuePair = iterator.next();
              str += nameValuePair.getName()+"="+nameValuePair.getValue();
              str +="&";
          }
          if(!TextUtils.isEmpty(str)){
              str =str.substring(0,str.lastIndexOf("&"));
              url = url +"?"+str;
          }
        }
        Log.e(TAG,"---------url=="+url);
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept-Encoding", "gzip");
        result = handleRequest(client.execute(request));
        Log.e(TAG,"----请求返回的报文："+result);
        return result;
    }

    /**
     * 处理Post请求
     * @param params  请求参数
     * @param url     请求url
     * @return
     * @throws IOException
     * @throws TimeoutException
     * @throws HttpException
     */
    public String postRequest(List<NameValuePair> params, String url) throws IOException, TimeoutException, HttpException {
        String result = null;
        HttpPost request = new HttpPost(url);
        Log.e(TAG,"----post url=="+url);
        request.setHeader("Accept-Encoding", "gzip");

        String paramsStr = "";

        Iterator<NameValuePair> iterator = params.iterator();
        while(iterator.hasNext()){
            NameValuePair nameValuePair = iterator.next();
            paramsStr += nameValuePair.getName()+"="+nameValuePair.getValue()+";";
        }
        Log.e(TAG,"-----post params="+paramsStr);
        HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
        request.setEntity(entity);

        result = handleRequest(client.execute(request));
        return result;
    }

    /**
     * 多参数，多文件的post请求
     *
     * @param stringParams
     * @param fileParams
     * @param url
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public String postRequest(List<NameValuePair> stringParams, List<NameValuePair> fileParams, String url) throws IOException, HttpException {
        String result = null;
        HttpPost request = new HttpPost(url);
        request.setHeader("Accept-Encoding", "gzip");
        MultipartEntity entity = new MultipartEntity();
        for (NameValuePair snv : stringParams) {
            entity.addPart(snv.getName(), new StringBody(snv.getValue(), Charset.forName("UTF-8")));
        }

        for (NameValuePair fnv : fileParams) {
            File file = new File(fnv.getValue());
            if (file.isFile() && !file.isDirectory()) {
                entity.addPart(fnv.getName(), new FileBody(file));
            }
        }
        request.setEntity(entity);

        result = handleRequest(client.execute(request));
        return result;
    }

    /**
     * put 请求
     * @param params
     * @param url
     * @return
     * @throws IOException
     * @throws HttpException
     */
    public String putRequest(List<NameValuePair> params, String url) throws IOException, HttpException {
        String result = null;
        HttpPut request = new HttpPut(url);
        Log.e(TAG,"----post url=="+url);
        request.setHeader("Accept-Encoding", "gzip");

        String paramsStr = "";

        Iterator<NameValuePair> iterator = params.iterator();
        while(iterator.hasNext()){
            NameValuePair nameValuePair = iterator.next();
            paramsStr += nameValuePair.getName()+"="+nameValuePair.getValue()+";";
        }
        Log.e(TAG,"-----post params="+paramsStr);
        HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
        request.setEntity(entity);

        result = handleRequest(client.execute(request));
        return result;
    }

    /**
     * 向server端发起请求，然后接受响应，读取数据
     * @param response
     * @return
     * @throws IOException
     * @throws HttpException
     */
    private String handleRequest(HttpResponse response) throws IOException, HttpException {
        String result = "";
        int httpStatusCode = response.getStatusLine().getStatusCode();
        if (httpStatusCode == HttpStatus.SC_OK) {
            Header contentEncoding = response
                    .getFirstHeader("Content-Encoding");
            if (contentEncoding != null
                    && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                GZIPInputStream gzip = new GZIPInputStream(new BufferedInputStream(response.getEntity().getContent()));
                StringBuilder builder =new StringBuilder();
                try{
                    if(gzip!=null){
                        byte[] b=new byte[256];
                        int len = 0;
                        while((len = gzip.read(b))!=-1){
                            builder.append(new String(b,0,len));
                        }
                    }
                }catch(Exception exception){

                }finally{
                    if(gzip!=null){
                        gzip.close();
                        gzip = null;
                    }
                }
                result = builder.toString();
                builder = null;
                contentEncoding = null;
            }else{
                result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            }
        } else {
            throw new HttpException("Error Response:" + response.getStatusLine().toString());
        }
        if(!TextUtils.isEmpty(result)){
            result = result.substring(result.indexOf("{"));
        }
        return result;
    }

}
