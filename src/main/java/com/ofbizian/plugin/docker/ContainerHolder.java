package com.ofbizian.plugin.docker;

import java.net.HttpURLConnection;
import java.net.URL;

import com.kpelykh.docker.client.DockerException;

public class ContainerHolder {
    private String url;
    private String containerName;

    public ContainerHolder(String url, String containerName) {
        this.url = url;
        this.containerName = containerName;
    }

    public void stop() throws DockerException {
        try {
            stopContainer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public String getContainerName() {
        return containerName;
    }

    private void stopContainer() throws Exception {
        String containerUrl = url +"/containers/" + containerName + "/stop?t=5";

        URL obj = new URL(containerUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("\nResponse : " + responseCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContainerHolder that = (ContainerHolder) o;

        if (containerName != null ? !containerName.equals(that.containerName) : that.containerName != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (containerName != null ? containerName.hashCode() : 0);
        return result;
    }
}
