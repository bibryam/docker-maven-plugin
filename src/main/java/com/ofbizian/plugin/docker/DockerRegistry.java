package com.ofbizian.plugin.docker;

import java.util.HashSet;
import java.util.Set;

public final class DockerRegistry {
    private static DockerRegistry instance;

    private Set<ContainerHolder> containers = new HashSet<ContainerHolder>(1);

    private DockerRegistry () {
    }

    public static DockerRegistry getInstance() {
        if (instance == null) {
            instance = new DockerRegistry();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        getInstance().unregisterAll();
                    }
                    catch (Exception e) {
                    }
                }
            });
        }
        return instance;
    }

    public synchronized boolean register(ContainerHolder container) {
        return containers.add(container);
    }

    public synchronized void unregister(ContainerHolder container) {
        try {
            container.stop();
        } catch (Exception e) {
        }
        containers.remove(container);
    }

    public synchronized void unregisterAll() {
        for (ContainerHolder container : containers) {
            unregister(container);
        }
    }
}
