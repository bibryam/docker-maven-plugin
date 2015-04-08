package com.ofbizian.plugin.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientImpl;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.LinkedList;
import java.util.List;

/**
 * User: bibryam
 * Date: 24/03/14
 */
public abstract class AbstractDockerMojo extends AbstractMojo {

    @Parameter(required = true)
    private List<Image> images = new LinkedList<Image>();

    @Parameter( property = "maven.docker.dockerUrl", defaultValue = "http://localhost:4243", required = true )
    private String dockerUrl;

    @Parameter(property = "maven.docker.skipStop", defaultValue = "false")
    private boolean skipStop;

    private DockerClient dockerClient;

    public List<Image> getImages() {
        return images;
    }

    public String getDockerUrl() {
        return dockerUrl;
    }

    public DockerClient getDockerClient(String url) {
        return dockerClient != null ? dockerClient : DockerClientImpl.getInstance(url);
    }

    public boolean isSkipStop() {
        return skipStop;
    }
}
