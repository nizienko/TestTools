package TestTools.httpnotifier;


import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by def on 07.11.14.
 */
public class HttpNotifier {
    private CloseableHttpClient httpclient;

    public HttpNotifier() {
        this.httpclient = HttpClients.createDefault();
    }

    public CloseableHttpClient getHttpClient() {
        return httpclient;
    }

    public String sendGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer responseBody = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    responseBody.append(line);
                    responseBody.append("\r\n");
                }
                br.close();
                return responseBody.toString();
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String sendPost(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        Iterator it = params.entrySet().iterator();
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            nvps.add(new BasicNameValuePair(pairs.getKey().toString(), pairs.getValue().toString()));
            it.remove();
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        try {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer responseBody = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    responseBody.append(line);
                    responseBody.append("\r\n");
                }
                br.close();
                return responseBody.toString();
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
