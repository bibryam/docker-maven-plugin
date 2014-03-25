Maven plugin for Docker
===================

The following maven configuration will start a docker container named *test-container* before integration tests starts.
It will then stop the container when integration tests finish.
If there are any containers started by this pluing but not stopped explicitely, they will be stopped when JMV shuts down.

    <plugin>
        <groupId>com.ofbizian</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <configuration>
            <containerName>test-container</containerName>
            <skipStop>true</skipStop>
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
