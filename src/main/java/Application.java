import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.UUID;

public class Application {


    public static void main(String[] args) {

        CloseableHttpClient httpClient = null;

        try {

            String protocol = "http";
            String host = "localhost";
            int port = 8088;
            String restApiPrefix = "api";
            StringBuilder sb = new StringBuilder(protocol)
                .append("://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(restApiPrefix)
                .append("/");
            String baseUrl = sb.toString();

            httpClient = HttpClients.createDefault();
            HttpClientContext httpClientContext = HttpClientContext.create();

            HttpPost postRequest = new HttpPost(baseUrl + "/message");
            postRequest.addHeader("content-type", "application/json");
            postRequest.addHeader("accept-language", "EN-en");
            postRequest.addHeader("charset", "UTF-8");

            Message msg = new Message();
            msg.setId(UUID.randomUUID().toString());
            msg.setContent("This is a message");
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(msg);
            HttpEntity entity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);
            postRequest.setEntity(entity);

            CloseableHttpResponse httpResponse = httpClient.execute(postRequest, httpClientContext);
            String responseJsonBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("Response: " + responseJsonBody);

            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
