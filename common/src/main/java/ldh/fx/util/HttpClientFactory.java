package ldh.fx.util;

import javafx.scene.control.Alert;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ldh on 2017/4/17.
 */
public class HttpClientFactory {

    private static HttpClientFactory instance;

    private HttpClientFactory() {
        init();
    }

    public static HttpClientFactory getInstance() {
        if (instance == null) {
            synchronized (HttpClientFactory.class) {
                if (instance == null) {
                    instance = new HttpClientFactory();
                }
            }
        }
        return instance;
    }

    private PoolingHttpClientConnectionManager poolConnManager;
    private final int maxTotalPool = 200;
    private final int maxConPerRoute = 20;
    private final int socketTimeout = 2000;
    private final int connectionRequestTimeout = 3000;
    private final int connectTimeout = 1000;

    public void init(){
        try {
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .build();
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolConnManager.setMaxTotal(maxTotalPool);
            poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();
            poolConnManager.setDefaultSocketConfig(socketConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CloseableHttpClient getConnection(){
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();
        if(poolConnManager !=null && poolConnManager.getTotalStats() != null){
            System.out.println("now client pool "+poolConnManager.getTotalStats().toString());
        }
        return httpClient;
    }

    public String get(String url) {
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpClient client = this.getConnection();
            CloseableHttpResponse response = client.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status  == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String resopnse="";
                if(entity != null) {
                    resopnse = EntityUtils.toString(entity,"utf-8");
                }
                return entity != null ? resopnse : null;
            } else {
                httpGet.abort();
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpGet.abort();
            throw new RuntimeException(e);
        }
    }

    public String post(String url, Map<String, Object> paramsMap) {
        String returnStr = null;
        if(url==null||"".equals(url)) {
            return returnStr;
        }
        HttpPost httpPost = new HttpPost(url);
        try {
            List<NameValuePair> nvps = new ArrayList<>();
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            System.out.println(" 开始发送 请求：url"+url);

            CloseableHttpClient client = this.getConnection();
            CloseableHttpResponse response = client.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String resopnse="";
                if(entity != null) {
                    resopnse= EntityUtils.toString(entity,"utf-8");
                }
                System.out.println("接收响应：url"+url+" status="+status);
                return entity != null ? resopnse : null;
            } else {
                httpPost.abort();
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception e) {
            httpPost.abort();
        }
        return returnStr;
    }
}
