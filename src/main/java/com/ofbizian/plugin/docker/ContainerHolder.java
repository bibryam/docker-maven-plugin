package com.ofbizian.plugin.docker;

import java.net.HttpURLConnection;
import java.net.URL;

import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.MojoExecutionException;

public class ContainerHolder {
    private String url;
    private String image;
    private String imageId;

    public ContainerHolder(String url, String image, String imageId) {
        this.url = url;
        this.image = image;
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public String getImageId() {
        return imageId;
    }

    public void stop() throws Exception {
        stopContainer();
    }

    private void stopContainer() throws Exception {
        String stopUrl = url +"/containers/" + imageId + "/stop?t=0";
        String deleteUrl = url +"/containers/" + imageId;

        URL obj = new URL(stopUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        int responseCode = con.getResponseCode();
        if (responseCode != 204) {
            throw new MojoExecutionException("Failed to stop container: " + responseCode);
        }

        URL deleteURL = new URL(deleteUrl);
        con = (HttpURLConnection) deleteURL.openConnection();
        con.setRequestMethod("DELETE");

        responseCode = con.getResponseCode();
        if (responseCode != 204) {
            throw new MojoExecutionException("Failed to delete container: " + responseCode);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContainerHolder that = (ContainerHolder) o;

        if (image != null ? !image.equals(that.image) : that.image != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
