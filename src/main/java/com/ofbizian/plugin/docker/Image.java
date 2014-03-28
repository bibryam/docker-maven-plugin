package com.ofbizian.plugin.docker;

/**
 * User: bibryam
 * Date: 27/03/14
 */
public class Image {
    private String name;

    private String containerConfig;

    private String hostConfig;

    public String getContainerConfig() {
        return containerConfig;
    }

    public void setContainerConfig(String containerConfig) {
        this.containerConfig = containerConfig;
    }

    public String getHostConfig() {
        return hostConfig;
    }

    public void setHostConfig(String hostConfig) {
        this.hostConfig = hostConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
