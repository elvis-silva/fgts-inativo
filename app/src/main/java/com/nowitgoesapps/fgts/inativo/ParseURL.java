package com.nowitgoesapps.fgts.inativo;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by elvis on 05/02/17.
 */

class ParseURL extends AsyncTask<String, Void, String> {

    private static final String TAG = ParseURL.class.getSimpleName();

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer buffer = new StringBuffer();
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            } };

            // Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Your code ...
            /*URL url = null;
            HttpsURLConnection conn = null;
            try {
                url = new URL("https://example.com");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }*/

            // http://www.survivingwithandroid.com/2014/02/android-weather-app-tutorial-step-by.html

            Log.d("JSwa", "Connecting to [" + strings[0] + "]");
            Document doc = Jsoup.connect(strings[0]).get();
            Log.d("JSwa", "Connected to [" + strings[0] + "]");
            // Get document (HTML page) title
            String title = doc.title();
            Log.d("JSwA", "Title [" + title + "]");
            buffer.append("Title: " + title + "\n");

            // Get meta info
            Elements metaElems = doc.select("meta");
            buffer.append("META DATA\n");
            for (Element metaElem : metaElems) {
                String name = metaElem.attr("name");
                String content = metaElem.attr("content");
                buffer.append("name [" + name + "] - content [" + content + "] \n");
            }

            Elements topicList = doc.select("h2.topic");
            buffer.append("Topic list\n");
            for (Element topic : topicList) {
                String data = topic.text();
                buffer.append("Data [" + data + "] \n");
            }

            Log.i(TAG, doc.html());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i(ParseURL.class.getSimpleName(), s);
    }
}