package ms_onboarding_data_store_api.data.adapter.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms_onboarding_data_store_api.data.config.AwsProperties;
import ms_onboarding_data_store_api.domain.port.OnboardingStoragePort;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3OnboardingStorageAdapter implements OnboardingStoragePort {

    private final S3Client s3Client;
    private final AwsProperties awsProperties;

    @Override
    public void save(String key, String content) {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(awsProperties.getS3().getBucket())
                .key(key)
                .contentType("application/json")
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(
                        content.getBytes(StandardCharsets.UTF_8)
                )
        );

        log.info("Evento almacenado en S3. bucket={}, key={}",awsProperties.getS3().getBucket(),key);
    }
}
