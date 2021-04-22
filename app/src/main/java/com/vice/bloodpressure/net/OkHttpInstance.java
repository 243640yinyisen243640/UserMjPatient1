package com.vice.bloodpressure.net;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yicheng on 2018/4/24.
 */

public class OkHttpInstance {

    private static OkHttpClient okHttpClient;


    public static synchronized OkHttpClient createInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpInstance.class) {
                if (okHttpClient == null) {
                    ParamsInterceptor paramsInterceptor = new ParamsInterceptor();
                    HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggerInterceptor());
                    logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(60, TimeUnit.SECONDS)      //设置连接超时
                            .readTimeout(60, TimeUnit.SECONDS)         //设置读超时
                            .writeTimeout(60, TimeUnit.SECONDS)        //设置写超时
                            .retryOnConnectionFailure(true)            //是否自动重连
                            //信任所有站点，此方法不安全，最好是能让接口给一个cer文件
                            .sslSocketFactory(createSSLSocketFactory(),new TrustAllCerts())
                            .hostnameVerifier(new TurstAllHostnameVerifier())
                            .addNetworkInterceptor(logInterceptor)
                            .addInterceptor(paramsInterceptor)
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    public static synchronized OkHttpClient getInstance() {
        return okHttpClient;
    }

    private static SSLSocketFactory createSSLSocketFactory(){
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,new TrustManager[]{new TrustAllCerts()},new SecureRandom());
            return sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class TrustAllCerts implements X509TrustManager{

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
    private static class TurstAllHostnameVerifier implements HostnameVerifier{

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
