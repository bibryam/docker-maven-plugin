package com.ofbizian.plugin.docker;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "stop", requiresDependencyResolution = ResolutionScope.RUNTIME, threadSafe = true)
class StopMojo extends AbstractDockerMojo {

    @Parameter(property = "maven.docker.skipErrorOnShutdown", defaultValue = "false")
    protected boolean skipErrorOnShutdown;

    @Parameter(property = "maven.docker.skipStop", defaultValue = "false")
    protected boolean skipStop;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skipStop) {
            getLog().info("skip stop");
            return;
        }
        try {
            for (Image image : getImages()) {
                DockerRegistry.getInstance().unregister(image.getName());
            }
        } catch (Exception e) {
            if (!skipErrorOnShutdown) {
                throw new MojoExecutionException("Stop error", e);
            }
        }
    }
}
