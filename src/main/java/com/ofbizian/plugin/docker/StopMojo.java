package com.ofbizian.plugin.docker;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "stop", requiresDependencyResolution = ResolutionScope.RUNTIME, threadSafe = true)
class StopMojo extends AbstractDockerMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            for (Image image : getImages()) {
                DockerRegistry.getInstance().unregister(image.getName());
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Stop error", e);
        }
    }
}
