package com.ofbizian.plugin.docker;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ContainerConfig;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;


@Mojo(name = "start", threadSafe = true, defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartMojo extends AbstractDockerMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        DockerClient dockerClient = null;
        try {
            dockerClient = getDockerClient(getDockerUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        DockerRegistry.setSkipAutoUnregister(isSkipStop());
        try {
            for (Image image : getImages()) {

                ContainerConfig containerConfig;
                if (image.getContainerConfig() != null && image.getContainerConfig().length() > 0) {
                    ObjectMapper mapper = new ObjectMapper();
                    containerConfig = mapper.readValue(image.getContainerConfig(), ContainerConfig.class);
                } else {
                    containerConfig = new ContainerConfig();
                }

                HostConfig hostConfig = null;

                if (image.getHostConfig() != null && image.getHostConfig().length() > 0) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        hostConfig = mapper.readValue(image.getHostConfig(), HostConfig.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    hostConfig = new HostConfig();
                }
                ExposedPort[] exposedPorts =  containerConfig.getExposedPorts();

                CreateContainerResponse container = dockerClient.createContainerCmd(image.getName()).withEnv(containerConfig.getEnv()).withExposedPorts(exposedPorts).exec();

                dockerClient.startContainerCmd(container.getId()).withPortBindings(hostConfig.getPortBindings()).exec();

                //TODO - Find a better way to check if application within the container has started successfully.
                Thread.sleep(15000);

                DockerRegistry.getInstance().register(new ContainerHolder(getDockerUrl(),image.getName(),container.getId()));
            }
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage());
        }
    }
}