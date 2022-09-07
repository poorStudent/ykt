package com.vms.ykt.Util;

import android.location.Location;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import org.jetbrains.annotations.NotNull;

import okhttp3.*;


import javax.net.ssl.*;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


class TrustAllCerts implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}

class TrustAllHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}

public class httpTool {

    static String TAG = httpTool.class.getSimpleName();

    private static OkHttpClient client;

    private static void getClient() {
        synchronized (httpTool.class) {
            if (client == null) {
                OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
                mBuilder.sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts());
                mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
                mBuilder.connectTimeout(10, TimeUnit.SECONDS);//设置连接超时时间
                mBuilder.readTimeout(20, TimeUnit.SECONDS);//设置读取超时时间
                mBuilder.followRedirects(false);
                mBuilder.followSslRedirects(false);
                mBuilder.addInterceptor(getInterceptor());
                client = mBuilder.build();
            }
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;

    }

    private static Interceptor getInterceptor() {

        Interceptor Interceptor = new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {

                Request vRequest = chain.request();
                Map<String,List<String>> header=vRequest.headers().toMultimap();
                String contentType= vRequest.body().contentType().toString();
                String bd= vRequest.body().toString();
                HttpUrl vHttpUrl =vRequest.url();
                String url = vHttpUrl.url().toString();

                System.out.println("url : " + url);
                System.out.println("body : " + bd);
                System.out.println("contentType : " + contentType);
                System.out.println("headers : " + JSONObject.toJSONString(header));

                Response vResponse = chain.proceed(vRequest);
                return vResponse;
            }
        };
        return Interceptor;
    }


    public static String doPostFile(String url, Map<String, Object> header, Map<String, Object> parameter, String filePath, String fileFormName) {
        return doPostFile(url, header, parameter, filePath, fileFormName, false);
    }

    public static String doPostFile(String url, Map<String, Object> header, String filePath, String fileFormName) {
        return doPostFile(url, header, null, filePath, fileFormName, false);
    }

    public static String doPostFile(String url, String filePath, String fileFormName) {
        return doPostFile(url, null, null, filePath, fileFormName, false);
    }

    public static String doPostFileBin(String url, Map<String, Object> header, Map<String, Object> parameter, String filePath, String fileFormName) {
        return doPostFile(url, header, parameter, filePath, fileFormName, true);
    }

    public static String doPostFileBin(String url, Map<String, Object> header, String filePath, String fileFormName) {
        return doPostFile(url, header, null, filePath, fileFormName, false);
    }

    public static String doPostFileBin(String url, String filePath, String fileFormName) throws Exception {
        return doPostFile(url, null, null, filePath, fileFormName, false);
    }

    /**
     * post 请求， 请求参数 并且 携带文件上传
     *
     * @param url
     * @param header
     * @param parameter
     * @param filePath,    文件
     * @param fileFormName 远程接口接收 file 的参数
     * @return
     * @throws Exception
     */
    public static String doPostFile(String url, Map<String, Object> header, Map<String, Object> parameter, String filePath, String fileFormName, boolean b) {

        // 创建一个请求 Builder
        Request.Builder builder = new Request.Builder();
        // 创建一个 request
        Request request = builder.url(url).build();
        //    Request.Builder request = builder.url(url);

        // 创建一个 Headers.Builder
        Headers.Builder headerBuilder = request.headers().newBuilder();

        // 装载请求头参数
        if (header != null) {
            Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
            while (headerIterator.hasNext()) {
                Map.Entry<String, Object> entry = headerIterator.next();
                headerBuilder.add(entry.getKey(), (String) entry.getValue());

            }
        }
        // 或者 FormBody.create 方式，只适用于接口只接收文件流的情况
        // RequestBody requestBody = FormBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Builder requestBuilder = new MultipartBody.Builder();

        // 状态请求参数
        if (parameter != null) {
            Iterator<Map.Entry<String, Object>> headerIterator = parameter.entrySet().iterator();
            while (headerIterator.hasNext()) {
                Map.Entry<String, Object> entry = headerIterator.next();
                requestBuilder.addFormDataPart(entry.getKey(), (String) entry.getValue());

            }
        }
        if (!filePath.isEmpty()) {
            // application/octet-stream
            File file = new File(filePath);
            if (b) {
                try {

                    byte[] bt = new byte[1024];
                    ByteArrayOutputStream varOutputStream = new ByteArrayOutputStream();
                    FileInputStream varStream = new FileInputStream(file);
                    int len;
                    while ((len = varStream.read(bt)) != -1) {
                        varOutputStream.write(bt, 0, len);
                    }
                    varStream.close();
                    varOutputStream.close();
                    requestBuilder.addFormDataPart(!fileFormName.isEmpty() ? fileFormName : "file", file.getName(), RequestBody.create(varOutputStream.toByteArray(), MediaType.parse("application/octet-stream")));
                } catch (Exception ParmsE) {
                    ParmsE.printStackTrace();
                }
            } else {
                requestBuilder.addFormDataPart(!fileFormName.isEmpty() ? fileFormName : "file", file.getName(), RequestBody.create(file, MediaType.parse("application/octet-stream")));

            }

        } else {
            return null;
        }

        // 设置自定义的 builder
        builder.headers(headerBuilder.build()).post(requestBuilder.build());

        // 然后再 build 一下
        try (Response execute = client.newCall(builder.build()).execute()) {
            //execute.header("");
            return execute.body().string();
        } catch (Exception ParmsE) {
            ParmsE.printStackTrace();
        }
        return "";
    }

    public static String downPic(String url, String pathName) {

        httpRespnose ret = null;
        String ck = "";
        try {
            ret = getT(url, null, null, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ck = ret.getSetCookie();

        InputStream inputStream = new ByteArrayInputStream(ret.getRespBytes());


        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(pathName));
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return ck;
    }


    /**
     * get 请求
     *
     * @param url    url
     * @param header 请求头参数
     * @param query  参数
     * @return
     */
    public static httpRespnose getT(String url, Map<String, Object> header, Map<String, Object> query, String body) {

        getClient();
        // 创建一个请求 Builder
        Request.Builder builder = new Request.Builder();

        // 装载请求的参数
        if (body != null && !body.isEmpty()) {
            url = url + "?" + body;
        }

        // 装载请求头参数
        if (header != null) {
            Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
            while (headerIterator.hasNext()) {
                Map.Entry<String, Object> entry = headerIterator.next();
                builder.addHeader(entry.getKey(), (String) entry.getValue());

            }
        }

        // 装载请求的参数
        if (query != null) {
            // 或者 FormBody.create 方式，只适用于接口只接收文件流的情况
            // RequestBody requestBody = FormBody.create(MediaType.parse("application/octet-stream"), file);
            StringBuilder tpbd= new StringBuilder();
            int pos=0;
            Iterator<Map.Entry<String, Object>> headerIterator = query.entrySet().iterator();
            while (headerIterator.hasNext()) {
                Map.Entry<String, Object> entry = headerIterator.next();
                if (pos>0){
                    tpbd.append("&");
                }
                tpbd.append(String.format("%s=%s", entry.getKey(),entry.getValue()));
                pos++;
            }
            url=url+"?"+tpbd.toString();

        }

        // 创建一个 request
        Request request = builder.url(url).build();

        httpRespnose varHttpRespnose = new httpRespnose();

        try (Response execute = client.newCall(request).execute()) {

            if (execute.code() == 503) {
                //  return doGets(url, header, query,body);

            }
            Headers Header = execute.headers();
            // System.out.println(Header.get("set-cookie"));
            if (execute.code() != 200) {
                if (execute.code() == 302) {

                }
                //Log.d(TAG, "getT: " + execute.code());
            }
            varHttpRespnose.setHearderFileds(Header.toMultimap());
            varHttpRespnose.setCode(String.valueOf(execute.code()));
            varHttpRespnose.setLocation(Header.get("Location"));
            String resp = execute.body().string();
            varHttpRespnose.setResp(resp);
            varHttpRespnose.setRespBytes(resp.getBytes(StandardCharsets.UTF_8));
            return varHttpRespnose;
        } catch (Exception ParmsE) {
            ParmsE.printStackTrace();
        }
        return varHttpRespnose;
    }

    /**
     * JSON数据格式请求
     *
     * @param url
     * @param header
     * @param json
     * @return
     */
    public static httpRespnose postT(String url, Map<String, Object> header, String json) {
        getClient();
        // 创建一个请求 Builder
        Request.Builder builder = new Request.Builder();

        String MediaTypes = "application/x-www-form-urlencoded; charset=utf-8";
        // 装载请求头参数
        if (header != null) {
            Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
            while (headerIterator.hasNext()) {
                Map.Entry<String, Object> entry = headerIterator.next();
                builder.addHeader(entry.getKey(), (String) entry.getValue());
                if (entry.getKey().contains("Content-Type")){
                    MediaTypes=(String) entry.getValue();
                }
            }
        }

        //application/octet-stream
        //application/text/x-markdown
        //"application/x-www-form-urlencoded; charset=utf-8";
        //application/json;charset=UTF-8

        RequestBody requestBody = FormBody.create(json, MediaType.parse(MediaTypes));

        // 创建一个 request
        Request request = builder.url(url).post(requestBody).build();;

        httpRespnose varHttpRespnose = new httpRespnose();

        try (Response execute = client.newCall(request).execute()) {
            if (execute.code() == 503) {

                //   return postT( url,  header, json);
            }
            Headers Header = execute.headers();

            if (execute.code() != 200) {
                if (execute.code() == 302) {

                }
                // Log.d(TAG, "postT: " + execute.code());
            }


            varHttpRespnose.setHearderFileds(Header.toMultimap());
            varHttpRespnose.setCode(String.valueOf(execute.code()));
            varHttpRespnose.setLocation(Header.get("Location"));
            String resp = execute.body().string();
            varHttpRespnose.setResp(resp);
            varHttpRespnose.setRespBytes(resp.getBytes(StandardCharsets.UTF_8));

            return varHttpRespnose;
        } catch (IOException ParmsE) {
            ParmsE.printStackTrace();
        }
        return varHttpRespnose;
    }


    public static httpRespnose getJ(String requestUrl, Map<String, Object> header, Map<String, Object> query, String body) {

        httpRespnose varRespnose = new httpRespnose();

        String resp = "";

        try {

            if (query != null) {
                StringBuilder data = new StringBuilder();
                Iterator<Map.Entry<String, Object>> headerIterator = query.entrySet().iterator();
                while (headerIterator.hasNext()) {
                    Map.Entry<String, Object> entry = headerIterator.next();
                    String key = entry.getKey();
                    String vu = (String) entry.getValue();
                    data.append("&");
                    data.append(key);
                    data.append("=");
                    data.append(vu);
                }
                String datas = data.toString().replaceFirst("&", "");
                requestUrl = requestUrl + "?" + datas;
            }

            if (body != null) {
                if (!body.isEmpty())
                    requestUrl = requestUrl + "?" + body;
            }

            URL url = new URL(requestUrl);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");

            if (header != null) {
                Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
                while (headerIterator.hasNext()) {
                    Map.Entry<String, Object> entry = headerIterator.next();
                    conn.setRequestProperty(entry.getKey(), (String) entry.getValue());

                }
            }


            varRespnose.setHearderFileds(conn.getHeaderFields());
            varRespnose.setCode(String.valueOf(conn.getResponseCode()));
            varRespnose.setLocation(conn.getHeaderField("Location"));

            //指定页面编码读取　否则乱码
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String cache = "";

            while ((cache = br.readLine()) != null) {
                resp += cache;
            }

            br.close();

            if (!(conn.getResponseCode() == 200)) {
                if (conn.getResponseCode() == 302) {
                    // return toLocationJ(Map, Location, "", 1);
                }

                // Log.d(TAG, "getJ: " + conn.getResponseCode());
            }


            varRespnose.setResp(resp);
            varRespnose.setRespBytes(resp.getBytes(StandardCharsets.UTF_8));

            return varRespnose;
        } catch (IOException e) {


            if (e.getMessage().contains("503")) {

                //return getJ(requestUrl,header,body);
            }
            e.printStackTrace();
        }
        return varRespnose;
    }

    public static httpRespnose postJ(String requestUrl, Map<String, Object> header, String json) {
        httpRespnose varRespnose = new httpRespnose();
        String resp = "";
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setInstanceFollowRedirects(false);
            if (header != null) {
                Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
                while (headerIterator.hasNext()) {
                    Map.Entry<String, Object> entry = headerIterator.next();
                    conn.setRequestProperty(entry.getKey(), (String) entry.getValue());

                }
            }
            if (json == null) json = "";
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
            pw.write(json);
            pw.flush();

            varRespnose.setHearderFileds(conn.getHeaderFields());
            varRespnose.setCode(String.valueOf(conn.getResponseCode()));
            varRespnose.setLocation(conn.getHeaderField("Location"));

            //指定页面编码读取　否则乱码
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String cache = "";

            while ((cache = br.readLine()) != null) {
                resp += cache;
            }
            br.close();
            pw.close();

            if (!(conn.getResponseCode() == 200)) {
                if (conn.getResponseCode() == 302) {

                    //return toLocationJ(Map, Location, "", 2);
                }

                // Log.d(TAG, "postJ: " + conn.getResponseCode());
            }

            varRespnose.setResp(resp);
            varRespnose.setRespBytes(resp.getBytes(StandardCharsets.UTF_8));


            return varRespnose;

        } catch (IOException e) {
            //服务器异常

            if (e.getMessage().contains("503")) {


                //return postJ(requestUrl , header,json);
            }
            //不建议在异常里面做逻辑业务 小型系统可以
            e.printStackTrace();

        }
        return varRespnose;
    }


    public static httpRespnose toLocation(Map<String, Object> Map, httpRespnose httpRespnose, String data, int type) {

        String Location = httpRespnose.getLocation();
        if (Location == null || Location.isEmpty()) return httpRespnose;
        Map.put("", httpRespnose.getSetCookie());
        if (type == 1) {
            return getJ(Location, Map, null, data);
        } else {
            return postJ(Location, Map, data);
            ///return getJ(Location, Map, data);
        }


    }

    private static httpRespnose toLocationT(Headers header, String s, int i) {
        String Location = header.get("Location");
        HashMap<String, Object> headers = new HashMap();
        headers.clear();
        for (String key : header.names()) {
            if (key == null || key.equals("")) continue;

            List<String> Values = header.values(key);
            StringBuilder strd = new StringBuilder();
            for (String vS : Values) {
                strd.append(vS);
            }
            String Value = strd.toString();
            if (key.contains("Accept-Encoding") || key.contains("Location")) continue;

            if (key.contains("Set-Cookie")) {
                headers.put("Cookie", Value);
            } else {
                headers.put(key, Value);
            }
            if (i == 1) {
                return getT(Location, headers, null, "");
            } else {
                return postT(Location, headers, "");

            }
        }
        return null;
    }

    public static httpRespnose toLocationJ(Map<String, List<String>> Map, String Location, String data, int type) {
        HashMap<String, Object> header = new HashMap();
        header.clear();
        Iterator<Map.Entry<String, List<String>>> headerIterator = Map.entrySet().iterator();
        while (headerIterator.hasNext()) {
            Map.Entry<String, List<String>> entry = headerIterator.next();
            String key = entry.getKey();

            if (key == null || key.equals("")) continue;

            List<String> Values = entry.getValue();
            StringBuilder strd = new StringBuilder();
            for (String vS : Values) {
                strd.append(vS);
            }
            String Value = strd.toString();
            if (key.contains("Accept-Encoding") || key.contains("Location")) continue;

            if (key.contains("Set-Cookie")) {
                header.put("Cookie", Value);
            } else {
                header.put(key, Value);
            }
        }
        System.out.println(Location);
        System.out.println(JSONObject.toJSONString(header));
        if (type == 1) {
            return getJ(Location, header, null, data);
        } else {
            return postJ(Location, header, data);
            //return getJ(Location, header, data);
        }


    }


}
