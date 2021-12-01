package om.simultechnology;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.exception.DockerClientException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.images.TimeLimitedLoggedPullImageResultCallback;

@Slf4j
public class Main {
    public static void main(String[] args) {
        System.out.println("start!");
//        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        DockerClientConfig standard = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        String registryUrl = standard.getRegistryUrl();
        System.out.println(registryUrl);
        System.out.println(standard.getDockerHost());

        DefaultDockerClientConfig config
                = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withRegistryEmail("info@baeldung.com")
//                .withRegistryPassword("baeldung")
//                .withRegistryUsername("baeldung")
//                .withDockerCertPath("/home/baeldung/.docker/certs")
//                .withDockerConfig("/home/baeldung/.docker/")
//                .withDockerTlsVerify("1")
                .withDockerHost(standard.getDockerHost().toString()).build();

        String image = "httpd:latest";
        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();
        try {
            InspectImageResponse response = dockerClient.inspectImageCmd(image).exec();
            System.out.println(response);
        } catch (NotFoundException notFoundException) {
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
            try {
                pullImageCmd.exec(new TimeLimitedLoggedPullImageResultCallback(log)).awaitCompletion();
            } catch (DockerClientException | InterruptedException e) {
                e.printStackTrace();
                // Try to fallback to x86
            }
        }

    }
}
