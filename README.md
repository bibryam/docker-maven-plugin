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
                <param>fab02</param>
                <param>fab03</param>
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
