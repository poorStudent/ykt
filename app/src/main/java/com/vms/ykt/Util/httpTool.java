package com.vms.ykt.Util;

import android.util.Log;

import okhttp3.*;


import javax.net.ssl.*;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
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

    static {
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts());
        mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
        mBuilder.connectTimeout(10, TimeUnit.SECONDS);//设置连接超时时间
        mBuilder.readTimeout(20, TimeUnit.SECONDS);//设置读取超时时间
        client = mBuilder.build();

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

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doGet(String url, Map<String, Object> header) {

        return doGet(url, header, "").getmResp();
    }

    public static httpRespnose doGet(String url, Map<String, Object> header, String body) {

        return doGets(url, header, null, body);
    }

    public static String doGet(String url, Map<String, Object> header, Map<String, Object> query) {

        return doGets(url, header, query, "").getmResp();
    }

    /**
     * get 请求
     *
     * @param url    url
     * @param header 请求头参数
     * @param query  参数
     * @return
     */
    public static httpRespnose doGets(String url, Map<String, Object> header, Map<String, Object> query, String body) {

        // 创建一个请求 Builder
        Request.Builder builder = new Request.Builder();
        // 创建一个 request
        if (body != null || !body.isEmpty()) {
            url = url + "?" + body;
        }
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


        // 装载请求的参数
        if (query != null) {
            Iterator<Map.Entry<String, Object>> headerIterator = query.entrySet().iterator();
            while (headerIterator.hasNext()) {
                Map.Entry<String, Object> entry = headerIterator.next();
                requestBuilder.addFormDataPart(entry.getKey(), (String) entry.getValue());

            }
        }

        // 设置自定义的 builder
        // 因为 get 请求的参数，是在 URL 后面追加  http://xxxx:8080/user?name=xxxx?sex=1
        //   builder.url(urlBuilder.build()).headers(headerBuilder.build());


        httpRespnose varMRespnose = new httpRespnose();
        ;
        try (Response execute = client.newCall(builder.build()).execute()) {

            if (execute.code() == 503) {
                //  return doGets(url, header, query,body);

            }
            if (execute.code() != 200) {
                Log.d(TAG, "doGets: "+execute.code());
            }


            varMRespnose.setHeaders(execute.headers());
            String resp = execute.body().string();

            varMRespnose.setmResp(resp);
            varMRespnose.setBytes(resp.getBytes(StandardCharsets.UTF_8));

            return varMRespnose;
        } catch (Exception ParmsE) {
            ParmsE.printStackTrace();
        }
        return varMRespnose;
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

    /**
     * JSON数据格式请求
     *
     * @param url
     * @param header
     * @param json
     * @return
     */

    public static httpRespnose postT(String url, Map<String, Object> header, String json) {
        // 创建一个请求 Builder
        Request.Builder builder = new Request.Builder();
        // 创建一个 request
        Request request = builder.url(url).build();

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

        // application/octet-stream
        RequestBody requestBody = FormBody.create(json, MediaType.parse("application/text/x-markdown"));

        // 设置自定义的 builder

        builder.headers(headerBuilder.build()).post(requestBody);

        httpRespnose varHttpRespnose = new httpRespnose();
        ;

        try (Response execute = client.newCall(builder.build()).execute()) {
            if (execute.code() == 503) {

                //   return postT( url,  header, json);
            }
            if (execute.code() != 200) {
                Log.d(TAG, "postT: "+execute.code());
            }


            varHttpRespnose.setHeaders(execute.headers());
            String resp = execute.body().string();
            varHttpRespnose.setmResp(resp);
            varHttpRespnose.setBytes(resp.getBytes(StandardCharsets.UTF_8));

            return varHttpRespnose;
        } catch (IOException ParmsE) {
            ParmsE.printStackTrace();
        }
        return varHttpRespnose;
    }

    public static String downPic(String url, String pathName) {

        httpRespnose ret = null;
        String ck = "";
        try {
            ret = doGets(url, null, null, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ck = ret.getHeaders().get("Set-Cookie");

        InputStream inputStream = new ByteArrayInputStream(ret.getBytes());


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

    public static httpRespnose getJ(String requestUrl, Map<String, Object> header, String body) {
        httpRespnose varRespnose = new httpRespnose();
        String resp = "";
        try {

            if (body != null) {
                if (!body.isEmpty())
                requestUrl = requestUrl + "?" + body;
            }
            URL url = new URL(requestUrl);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (header != null) {
                Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
                while (headerIterator.hasNext()) {
                    Map.Entry<String, Object> entry = headerIterator.next();
                    conn.setRequestProperty(entry.getKey(), (String) entry.getValue());

                }
            }


            if (!(conn.getResponseCode() == 200)) {
                Log.d(TAG, "getJ: "+conn.getResponseCode());
            }


            //指定页面编码读取　否则乱码
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String cache = "";

            while ((cache = br.readLine()) != null) {
                resp += cache;
            }

            br.close();

            varRespnose.setmResp(resp);
            varRespnose.setBytes(resp.getBytes(StandardCharsets.UTF_8));
            varRespnose.setmHearderFileds(conn.getHeaderFields());
            varRespnose.setCode(String.valueOf(conn.getResponseCode()));
            return varRespnose;
        } catch (IOException e) {

            if (e.getMessage().contains("503")) {


                //   return getJ(requestUrl,header,body);
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
            if (header != null) {
                Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
                while (headerIterator.hasNext()) {
                    Map.Entry<String, Object> entry = headerIterator.next();
                    conn.setRequestProperty(entry.getKey(), (String) entry.getValue());

                }
            }
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
            pw.write(json);
            pw.flush();

            if (!(conn.getResponseCode() == 200)) {
                Log.d(TAG, "postJ: " + conn.getResponseCode());
            }
            //指定页面编码读取　否则乱码
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String cache = "";

            while ((cache = br.readLine()) != null) {
                resp += cache;
            }
            br.close();
            pw.close();

            varRespnose.setmResp(resp);
            varRespnose.setBytes(resp.getBytes(StandardCharsets.UTF_8));
            varRespnose.setmHearderFileds(conn.getHeaderFields());
            varRespnose.setCode(String.valueOf(conn.getResponseCode()));
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
}
