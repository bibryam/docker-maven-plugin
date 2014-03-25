package com.ofbizian.plugin.docker;

import com.kpelykh.docker.client.DockerClient;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * User: bibryam
 * Date: 24/03/14
 */
public abstract class AbstractDockerMojo extends AbstractMojo {

    @Parameter(property = "maven.docker.containerName", required = true)
    private String containerName;

    @Parameter( property = "maven.docker.dockerUrl", defaultValue = "http://localhost:4243", required = true )
    private String dockerUrl;

    private DockerClient dockerClient;

    protected String getContainerName() {
        return containerName;
    }

    protected String getDockerUrl() {
        return dockerUrl;
    }

    protected  DockerClient getDockerClient(String url) {
        return dockerClient != null ? dockerClient : new DockerClient(url);
    }
}
