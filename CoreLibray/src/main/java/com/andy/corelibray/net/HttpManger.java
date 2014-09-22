package com.andy.corelibray.net;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.andy.commonlibrary.net.HttpClientHandleInterface;
import com.andy.commonlibrary.net.exception.MessageException;
import com.andy.corelibray.R;

import org.apache.http.HttpException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by andy_lv on 2014/8/23.
 */
public class HttpManger {

    private final String TAG = "HttpManger";
    private static HttpManger instance ;

    private HttpManger(){

    }

    public static HttpManger getInstance(){
       if(null == instance){
           instance = new HttpManger();
       }
       return instance;
    }

    /**
     *  Post 请求
     * @param bussinessParams
     */
    public HttpAsyncTask postSend(final Context context,final BussinessParams bussinessParams,
                        final HttpClientHandleInterface httpClientHandle){
        HttpAsyncTask httpAsyncTask =
                new HttpAsyncTask(context,bussinessParams,httpClientHandle, HttpClientUtil.RequestType.POST);
        httpAsyncTask.execute();
        return httpAsyncTask;
    }

    /**
     * Get 请求
     * @param context
     * @param bussinessParams
     * @param httpClientHandle
     */
    public HttpAsyncTask getSend(final Context context,final BussinessParams bussinessParams,
                         final HttpClientHandleInterface httpClientHandle){
        HttpAsyncTask httpAsyncTask =
                new HttpAsyncTask(context,bussinessParams,httpClientHandle, HttpClientUtil.RequestType.GET);
        httpAsyncTask.execute();
        return httpAsyncTask;
    }

    /**
     * Put 请求
     * @param context
     * @param bussinessParams
     * @param httpClientHandle
     * @return
     */
    public HttpAsyncTask putSend(final Context context,final BussinessParams bussinessParams,
                                 final HttpClientHandleInterface httpClientHandle){
        HttpAsyncTask httpAsyncTask =
                new HttpAsyncTask(context,bussinessParams,httpClientHandle, HttpClientUtil.RequestType.PUT);
        httpAsyncTask.execute();
        return httpAsyncTask;
    }


    /**
     * 网络请求异步任务类
     */
    public class HttpAsyncTask extends AsyncTask<Void,Integer,Boolean>{

        private String  resultObj;
        private MessageException messageException;
        private Context context ;
        private BussinessParams bussinessParams;
        private HttpClientHandleInterface httpClientHandle;
        private HttpClientUtil.RequestType requestType;

        public HttpAsyncTask(final Context context,final BussinessParams bussinessParams,
                             final HttpClientHandleInterface httpClientHandle,
                             final HttpClientUtil.RequestType requestType){
            this.context = context;
            this.bussinessParams = bussinessParams;
            this.httpClientHandle = httpClientHandle;
            this.requestType = requestType ;
        }

        @Override
        protected void onPreExecute() {
            Log.e(TAG,"---------onPreExecute----------");
            if(null != httpClientHandle){
                httpClientHandle.start();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.e(TAG,"---------doInBackground----------");
            boolean result = false;
            try {
                String responseString = HttpClientUtil.getInstance().send(requestType
                        ,bussinessParams);
                Log.e(TAG,"---------返回的报文----"+responseString);
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if(jsonObject != null){
                        String code          = jsonObject.optString("code");
                        String message       = jsonObject.optString("message");
                        boolean success      = jsonObject.optBoolean("success");
                        if(success){
                            resultObj = responseString;
                        }else{
                            messageException = new MessageException(code,message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                result = true;
            } catch (TimeoutException e) {
                e.printStackTrace();
                messageException = new MessageException(context.getString(R.string.timeout_status_code),
                        context.getString(R.string.timeout_exception_message));
            } catch (IOException e) {
                e.printStackTrace();
                messageException = new MessageException(context.getString(R.string.io_statuscode),
                        context.getString(R.string.io_exception_message));
            } catch (HttpException e) {
                e.printStackTrace();
                messageException = new MessageException(context.getString(R.string.general_statuscode),
                        context.getString(R.string.general_http_exception_message));
            }
            return  result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            Log.e(TAG,"---------onPostExecute----------");
            if(null != httpClientHandle) {
                httpClientHandle.finish();
                if (null != resultObj) {
                    httpClientHandle.success(resultObj);
                } else {
                    httpClientHandle.fail(messageException);
                }
            }
        }

        @Override
        protected void onCancelled() {
            Log.e(TAG,"---------onCancelled----------");
            super.onCancelled();
            if(null != httpClientHandle){
                httpClientHandle.finish();
            }
        }
    }
}
