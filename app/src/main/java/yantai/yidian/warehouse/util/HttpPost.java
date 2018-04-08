package yantai.yidian.warehouse.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Created by 李非 on 2018/4/3.
 */

public class HttpPost {
    public static void sendHttpRequest(final String address,final String postData,final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConn = null;
                String responseData = "";
                int responseCode = 0;
                try {
                    URL url = new URL(address);
                    urlConn = (HttpURLConnection) url.openConnection();

                    urlConn.setConnectTimeout(8000);
                    urlConn.setReadTimeout(8000);
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setRequestMethod("POST");
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    urlConn.addRequestProperty("contentType",
                            "application/x-www/form-urlencoded");
                    urlConn.setRequestProperty("accept", "application/json");
                    urlConn.setRequestProperty("Charset", "UTF-8");
                  //  urlConn.addRequestProperty("Cookie","92D84AAD121190462E763B7D773F144C");
                 /*   byte[] writebytes=postData.getBytes();
                    urlConn.setRequestProperty("Content-Length",String.valueOf(writebytes.length));
                    Log.d(TAG, "run: 11111");
                    urlConn.getOutputStream().write(postData.getBytes());*/
                    //输入流
                    DataOutputStream outStream = new DataOutputStream(urlConn.getOutputStream());
                    outStream.writeBytes(URLEncoder.encode(postData, "UTF-8"));     //作用，提交的数据可以是中文
                    outStream.flush();
                    outStream.close();
                    responseCode = urlConn.getResponseCode();
                    if (responseCode == 200)//200-OK
                    {
                        //输出流
                        urlConn.setInstanceFollowRedirects(false);
                        StringBuffer strBuffer = new StringBuffer();
                        String strLine = null;
                        InputStream inStream = urlConn.getInputStream();
                        InputStreamReader isr=new InputStreamReader(inStream,"UTF-8");
                        BufferedReader bufReader = new BufferedReader(isr);

                        //BufferedReader bufReader = new BufferedReader(new InputStreamReader(inStream));

                        while ((strLine = bufReader.readLine()) != null) {
                            strBuffer.append(strLine);
                        }

                        responseData = strBuffer.toString();
                        responseData = URLDecoder.decode(responseData, "UTF-8");
                        if(listener!=null){
                            listener.onFinish(responseData);
                        }
                        inStream.close();

                    } else {
                        listener.onFinish("数据提交失败");


                    }
                } catch (MalformedURLException e) {
                    if(listener!=null){
                        listener.onError(e);
                    }
                } catch (IOException e) {
                    if(listener!=null){
                        listener.onError(e);
                    }
                } finally {
                    if (urlConn != null) {
                        urlConn.disconnect();
                    }
                }
            }
        }).start();
    }
}
