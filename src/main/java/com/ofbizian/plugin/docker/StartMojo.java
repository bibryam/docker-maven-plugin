package com.ofbizian.plugin.docker;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.NotFoundException;
import com.kpelykh.docker.client.model.BoundHostVolumes;
import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.ContainerCreateResponse;
import com.kpelykh.docker.client.model.HostConfig;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;

@Mojo( name = "start", threadSafe = true )
public class StartMojo extends AbstractDockerMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        DockerClient dockerClient = getDockerClient(getDockerUrl());

        getPluginContext().put("images", getImages());

        try {
            for (Image image : getImages()) {
                ContainerConfig containerInfo;
                if (image.getContainerConfig() != null && image.getContainerConfig().length() > 0) {
                    ObjectMapper mapper = new ObjectMapper();
                    containerInfo = mapper.readValue(image.getContainerConfig(), ContainerConfig.class);
                }  else {
                    containerInfo = new ContainerConfig();
                }
                containerInfo.setImage(image.getName());

                HostConfig hostInfo;
                if (image.getHostConfig() != null && image.getHostConfig() .length() > 0) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.getDeserializationConfig().addMixInAnnotations(HostConfig.class, IgnoreFooSetValueIntMixIn.class);
                    hostInfo = mapper.readValue(image.getHostConfig() , HostConfig.class);
                }  else {
                    hostInfo = new HostConfig();
                }

                ContainerCreateResponse containerResponse;
                try {
                    containerResponse = dockerClient.createContainer(containerInfo);
                } catch (NotFoundException nfe) {
                    dockerClient.pull(image.getName());
                    containerResponse = dockerClient.createContainer(containerInfo);
                }

//                getPluginContext().put(image, containerResponse.getId());

//                Ports portBindings = new Ports();
//                Ports.Port port = new Ports.Port("tcp", "6379", "0.0.0.0", "6379");
//                portBindings.addPort(port);
//                hostConfig.setPortBindings(portBindings);

                dockerClient.startContainer(containerResponse.getId(), hostInfo);

                DockerRegistry.getInstance().register(new ContainerHolder(getDockerUrl(), image.getName(), containerResponse.getId()));
            }
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage());
        }
    }

    abstract class IgnoreFooSetValueIntMixIn {
        @JsonIgnore
        public abstract void setBinds(final BoundHostVolumes volumes);
    }
}