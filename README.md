Maven plugin for Docker
===================

This plugin allows managing multiple Docker containers from a maven build process.

###Prerequisites
Locally running docker with TCP sockets enabled:
On RHEL thats in `/usr/lib/systemd/system/docker.service`

`ExecStart=/usr/bin/docker -d -H tcp://127.0.0.1:4243 -H unix:///var/run/docker.sock`

###Usage
The **start** goal will create a container instance from an image and start it.
If the image is not available locally, the plugin will pull the image from the internet.

The **stop** goal will stop the container instances and remove them from the local registry.
The image will remain on the local repository.

If containers are not stopped explicitly using **stop** goal, the plugin by default will try to stop and delete all containers started from **start** goal.
If you want to keep containers running after maven build has finished, use *skipStop* option to prevent stopping the containers.

*containerConfig* option allows configuring container creation parameters.
*hostConfig* option allows configuring container start parameters.

###Examples
Here is the simplest example to start a container from publicly available "busybox" image and stop it at the end of the build:

    <plugin>
        <groupId>com.ofbizian</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>1.0.0</version>
        <configuration>
            <images>
                <image>
                    <name>busybox</name>
                </image>
            </images>
        </configuration>
        <executions>
            <execution>
                <id>start-docker</id>
                <phase>pre-integration-test</phase>
                <goals>
                    <goal>start</goal>
                </goals>
            </execution>
        </executions>
    </plugin>

Next is a complete example with all the possible configurations:

    <plugin>
        <groupId>com.ofbizian</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>1.0.0</version>
        <configuration>
            <dockerUrl>http://localhost:4243</dockerUrl>
            <images>
                <image>
                    <name>dockerfile/redis</name>
                    <containerConfig>
                        <![CDATA[
                        {"Hostname":"",
                            "PortSpecs":null,
                            "User":"",
                            "Tty":false,
                            "OpenStdin":false,
                            "StdinOnce":false,
                            "Memory":0,
                            "MemorySwap":0,
                            "CpuShares":0,
                            "AttachStdin":false,
                            "AttachStdout":false,
                            "AttachStderr":false,
                            "Env":null,
                            "Cmd":null,
                            "Dns":null,
                            "Volumes":null,
                            "VolumesFrom":"",
                            "Entrypoint":[

                            ],
                            "NetworkDisabled":false,
                            "Privileged":false,
                            "WorkingDir":"",
                            "Domainname":"",
                            "ExposedPorts":null,
                            "OnBuild":null}
                        ]]>
                    </containerConfig>
                    <hostConfig>
                        <![CDATA[
                        {"ContainerIDFile": null, "LxcConf": null, "Links": null, "PortBindings": {
                            "6379/tcp": [
                                {
                                    "HostIp": "0.0.0.0",
                                    "HostPort": "6379"
                                }
                            ]
                        }, "Privileged": false, "PublishAllPorts": false}
                        ]]>
                    </hostConfig>
                </image>
                <image>
                    <name>busybox</name>
                </image>
            </images>
        </configuration>
        <executions>
            <execution>
                <id>start-docker</id>
                <phase>pre-integration-test</phase>
                <goals>
                    <goal>start</goal>
                </goals>
            </execution>
            <execution>
                <id>stop-docker</id>
                <phase>post-integration-test</phase>
                <goals>
                    <goal>stop</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
