package com.client.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class HttpClient {

    private final Integer timeout;
    
    public HttpClient() {
        timeout = 60000; //60min
    }

    private String request(
        String url, 
        String requestMethod, 
        String jwtToken,
        Map<String,String> queryParams, 
        String body) throws IOException {
            
        HttpURLConnection connection = null;
        URL u;

        //QueryGenerator
        if(queryParams != null && queryParams.size() > 0) {
            String firstKey = queryParams.keySet().iterator().next();
            String firstParam = queryParams.get(firstKey);
            StringBuilder query = new StringBuilder();
            query.append("?").append(firstKey).append(":").append(firstParam);
            queryParams.remove(firstKey);
            queryParams.forEach((key, value) -> {
                query.append("&").append(key).append(":").append(value);
            });
            u = new URL(url + query.toString());
        } else {
            u = new URL(url);
        }

        connection = (HttpURLConnection)u.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
        connection.setAllowUserInteraction(false);
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);

        if (jwtToken != null) {
            connection.setRequestProperty("Authorization","Bearer " + jwtToken);
        }

        //BodyInput
        if (body != null) {
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");    
            osw.write(body.toString());
            osw.flush();
            osw.close();
            os.close(); 
        }
        
        connection.connect();
        int status = connection.getResponseCode();
        String test = connection.getResponseMessage();
        InputStream stream;
        if (status < HttpURLConnection.HTTP_BAD_REQUEST){
            stream = connection.getInputStream();
        }else{
            stream = connection.getErrorStream();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();
        if (connection != null) {
            connection.disconnect();
        }
        return sb.toString();
    }

    public String getRequest(String url) throws IOException {
        return request(url, "GET", null, null, null);
    }

    public String getRequest(String url, String token) throws IOException {
        return request(url, "GET", token, null, null);
    }

    public String getRequest(String url, Map<String, String> queryParams, String token) throws IOException {
        return request(url, "GET", token, queryParams, null);
    }

    public String getRequest(String url, Map<String, String> queryParams) throws IOException {
        return request(url, "GET", null, queryParams, null);
    }

    public String postRequest(String url, String jsonBody) throws IOException {
        return request(url, "POST", null, null, jsonBody);
    }

    public String postRequest(String url, String jsonBody, String token) throws IOException {
        return request(url, "POST", token, null, jsonBody);
    }

    public String putRequest(String url) throws IOException {
        return request(url, "PUT", null, null, null);
    }

    public String putRequest(String url, String  token) throws IOException {
        return request(url, "PUT", token, null, null);
    }

    public String putRequest(String url, Map<String, String> queryParams) throws IOException {
        return request(url, "PUT", null, queryParams, null);
    }

    public String putRequest(String url, Map<String, String> queryParams, String token) throws IOException {
        return request(url, "PUT", token, queryParams, null);
    }

    public String putRequest(String url, String jsonBody, String token) throws IOException {
        return request(url, "PUT", token, null, jsonBody);
    }

    public String deleteRequest(String url) throws IOException {
        return request(url, "DELETE", null, null, null);
    }

    public String deleteRequest(String url, String token) throws IOException {
        return request(url, "DELETE", token, null, null);
    }

    public String deleteRequest(String url, Map<String, String> queryParams) throws IOException {
        return request(url, "DELETE", null, queryParams, null);
    }

    public String deleteRequest(String url, Map<String, String> queryParams, String token) throws IOException {
        return request(url, "DELETE", token, queryParams, null);
    } 
}