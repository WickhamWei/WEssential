package io.github.wickhamwei.wessential.wupdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

public class WUpdateChecker {
    private final String projectUrlString;
    private HttpURLConnection httpURLConnection;
    private String newestVersionString;
    private String newestVersionPTimeString;
    private boolean networkNormal = true;

    public WUpdateChecker(String projectUrlString) {
        this.projectUrlString = projectUrlString;
    }

    public String getNewestVersionPTimeString() {
        return newestVersionPTimeString;
    }

    public String getNewestVersionString() {
        return newestVersionString;
    }

    public boolean isNetworkNormal() {
        return networkNormal;
    }

    public void getUpdate() {
        URL url;
        try {
            url = new URL(projectUrlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.connect();
            BufferedReader responseReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            String allOriginJsonString = responseReader.readLine();

            Gson gson = new Gson();
            Github github = gson.fromJson(allOriginJsonString, Github.class);
            newestVersionString = github.getLastVersion();
            newestVersionPTimeString = github.getLastVersionPublishedTime();
        } catch (IOException e) {
            networkNormal = false;
        } finally {
            httpURLConnection.disconnect();
        }
    }
}