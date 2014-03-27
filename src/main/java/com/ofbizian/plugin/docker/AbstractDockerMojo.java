package com.ofbizian.plugin.docker;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.kpelykh.docker.client.DockerClient;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * User: bibryam
 * Date: 24/03/14
 */
public abstract class AbstractDockerMojo extends AbstractMojo {

    @Parameter(required = true)
    private List<Image> images = new LinkedList<Image>();


    @Parameter( property = "maven.docker.dockerUrl", defaultValue = "http://localhost:4243", required = true )
    private String dockerUrl;

//    @Parameter( property = "maven.docker.params", defaultValue = "http://localhost:4243", required = true )
//    private Map<String, Object> params;

    private DockerClient dockerClient;

    protected List<Image> getImages() {
        return images;
    }

    protected String getDockerUrl() {
        return dockerUrl;
    }

    protected  DockerClient getDockerClient(String url) {
        return dockerClient != null ? dockerClient : new DockerClient(url);
    }
}
