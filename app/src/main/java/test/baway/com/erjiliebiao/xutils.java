package test.baway.com.erjiliebiao;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * name:周振辉
 * 时间：2017/9/1 10:04
 * 类描述：
 */

public class xutils {
     public static String postUrlConnect(String urlPath, Map<String, Object> map) {
                 StringBuffer sbRequest = new StringBuffer();
                 if (map != null && map.size() > 0) {
                     for (String key : map.keySet()) {
                         sbRequest.append(key + "=" + map.get(key) + "&");
                     }
                 }
                 String request = sbRequest.substring(0, sbRequest.length() - 1);

                 try {
                     URL url = new URL(urlPath);
                     HttpURLConnection httpurl = (HttpURLConnection) url.openConnection();
                     httpurl.setRequestMethod("POST");
                     //设置连接主机超时
                     httpurl.setConnectTimeout(30000);
                     //设置从主机读取数据超时
                     httpurl.setReadTimeout(30000);
                     httpurl.setDoInput(true);
                     httpurl.setDoOutput(true);
     				//读取数据
                     OutputStream os = httpurl.getOutputStream();
                     os.write(request.getBytes());
                    //刷新
                     os.flush();
                     //判断返回值是否正确
                     if (httpurl.getResponseCode() == 200) {
                         InputStream in = httpurl.getInputStream();
                         StringBuffer sb = new StringBuffer();
                      //创建字符数组
                         byte[] buff = new byte[1024];
                         int len = -1;
                         while ((len = in.read(buff)) != -1) {
                             sb.append(new String(buff, 0, len, "utf-8"));
                         }
                         //关闭流
                         in.close();
                         os.close();
                         httpurl.disconnect();
                         return sb.toString();
                     } else {
                         return null;
                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 return urlPath;
             }

             public static String getUrlConnect(String urlPath) {

                 try {
                     //获取地址
                     URL url = new URL(urlPath);
                     HttpURLConnection httpurl = (HttpURLConnection) url.openConnection();
                     httpurl.connect();
                     //判断返回值
                     if (httpurl.getResponseCode() == 200) {
                         InputStream in = httpurl.getInputStream();
                         StringBuffer sb = new StringBuffer();
                         byte[] buff = new byte[1024];
                         int len = -1;
                         while ((len = in.read(buff)) != -1) {
                             sb.append(new String(buff, 0, len, "utf-8"));
                         }
                         in.close();
                         httpurl.disconnect();
                         return sb.toString();
                     } else {
                         return null;
                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 return null;
             }
}
