Maven plugin for Docker
===================

The following maven configuration will start a docker container named *test-container* before integration tests starts.
It will then stop the container when integration tests finish.
If there are any containers started by this plugin but not stopped explicitly, they will be stopped when JMV shuts down.

    <plugin>
        <groupId>com.ofbizian</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>${project.version}</version>
            <configuration>
                <images>
                    <image>
                        <name>dockerfile/redis</name>
                        <hostConfig>
                            <![CDATA[
                            {"ContainerIDFile":null,"LxcConf":null,"Links":null,"PortBindings":{"6379/tcp":[{"HostIp":"0.0.0.0","HostPort":"6379"}]},"Privileged":false,"PublishAllPorts":false}
                            ]]>
                        </hostConfig>
                        <containerConfig>
                            <![CDATA[
                            {"Hostname":"","PortSpecs":null,"User":"","Tty":false,"OpenStdin":false,"StdinOnce":false,"Memory":0,"MemorySwap":0,"CpuShares":0,"AttachStdin":false,"AttachStdout":false,"AttachStderr":false,"Env":null,"Cmd":null,"Dns":null,"Image":"dockerfile/redis","Volumes":null,"VolumesFrom":"","Entrypoint":[],"NetworkDisabled":false,"Privileged":false,"WorkingDir":"","Domainname":"","ExposedPorts":null,"OnBuild":null}
                            ]]>
                        </containerConfig>
                    </image>
                    <image>
                        <name>tutum/redis</name>
                        <hostConfig>
                            <![CDATA[
                            {"ContainerIDFile":null,"LxcConf":null,"Links":null,"PortBindings":{"6479/tcp":[{"HostIp":"0.0.0.0","HostPort":"6479"}]},"Privileged":false,"PublishAllPorts":false}
                            ]]>
                        </hostConfig>
                        <containerConfig>
                            <![CDATA[
                            {"Hostname":"","PortSpecs":null,"User":"","Tty":false,"OpenStdin":false,"StdinOnce":false,"Memory":0,"MemorySwap":0,"CpuShares":0,"AttachStdin":false,"AttachStdout":false,"AttachStderr":false,"Env":null,"Cmd":null,"Dns":null,"Image":"tutum/redis","Volumes":null,"VolumesFrom":"","Entrypoint":[],"NetworkDisabled":false,"Privileged":false,"WorkingDir":"","Domainname":"","ExposedPorts":null,"OnBuild":null}
                            ]]>
                        </containerConfig>
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
