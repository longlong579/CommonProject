package self.xhl.com.common.update.download;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import self.xhl.com.common.update.FileConstants;
import self.xhl.com.common.utils.CryptoUtil;

/**
 * @author bingo
 * @version 1.0.0
 */

public class DownLoadUtil {


    public interface DownLoadListener {

        void download(long fileSize, long downLoadFileSize);

        void downloadComplete(long fileSize, long downLoadFileSize,
                              String apkFile);

        void downloadFailed();

    }

    /**
     * 下载apk
     *
     * @param url
     */
    public static void downloadAPK(final String url,
                                   final DownLoadListener listener) {
        final  int OUTTIME = 20000;
        new AsyncTask<Void, Void, String>() {

            File file = null;
            long fileSize = 0;
            long downLoadFileSize = 0;
            private boolean isSuccess = false;

            @Override
            protected String doInBackground(Void... params) {
                if (!FileConstants.FILE_APK.exists()) {
                    FileConstants.FILE_APK.mkdirs();
                }
                file = new File(FileConstants.FILE_APK,
                        CryptoUtil.getMD5(url) + ".apk");
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream outStream;
                try {

                    initSSLConfig();

                    outStream = new FileOutputStream(file);
                    HttpURLConnection realConn = null;

                    URL realUrl = new URL(url);

                    realConn = (HttpURLConnection) realUrl.openConnection();
                    realConn.setConnectTimeout(OUTTIME);
                    if (realConn.getResponseCode() == 200) {
                        fileSize = realConn.getContentLength();
                        InputStream inStream = realConn.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        long dvalue = fileSize / 100;
                        long temp = 0;
                        while ((len = inStream.read(buffer)) != -1) {
                            temp += len;
                            downLoadFileSize = downLoadFileSize + len;
                            if (temp >= dvalue) {
                                temp = 0;
                                publishProgress();
                            }
                            outStream.write(buffer, 0, len);
                        }
                        publishProgress();
                        isSuccess = true;
                        outStream.close();
                        inStream.close();
                        Log.i("info", "" + len);
                    } else {
                        isSuccess = false;
                        file.delete();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    isSuccess = false;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    isSuccess = false;
                } catch (IOException e) {
                    e.printStackTrace();
                    isSuccess = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }

            /**
             * 初始化SSL 相关的一些配置
             * @throws NoSuchAlgorithmException
             * @throws KeyManagementException
             */
            private void initSSLConfig() throws NoSuchAlgorithmException, KeyManagementException {
                TrustManager[] trustAllCerts = getTrustAllManagers();

                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new SecureRandom());

                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {

                        return true;
                    }
                });
            }

            /**
             * 新人所有服务器，无需验证服务端证书
             * @return
             */
            @NonNull
            private TrustManager[] getTrustAllManagers() {
                return new TrustManager[] { new X509TrustManager() {

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[] {};
                    }

                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }
                } };
            }

            @Override
            protected void onPostExecute(String result) {
                Log.i("info", "" + isSuccess);
                if (isSuccess) {
                    listener.downloadComplete(fileSize, downLoadFileSize,
                            file.getAbsolutePath());
                } else {
                    listener.downloadFailed();
                }
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                listener.download(fileSize, downLoadFileSize);
            }

        }.execute((Void) null);
    }

}