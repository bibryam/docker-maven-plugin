package com.ofbizian.plugin.docker;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo( name = "start", threadSafe = true )
public class StartMojo extends AbstractDockerMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        DockerClient dockerClient = getDockerClient(getDockerUrl());

        try {
            dockerClient.startContainer(getContainerName());
            System.out.println(dockerClient.info());

            DockerRegistry.getInstance().register(new ContainerHolder(getDockerUrl(), getContainerName()));
        } catch (DockerException e) {
            e.printStackTrace();
        }
    }
}