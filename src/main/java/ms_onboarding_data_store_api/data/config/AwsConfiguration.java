package ms_onboarding_data_store_api.data.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.regions.Region;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(AwsProperties.class)
public class AwsConfiguration {

    @Bean
    SqsClient sqsClient(AwsProperties properties) {
        return SqsClient.builder()
                .endpointOverride(URI.create(properties.getEndpointUrl()))
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(credentialsProvider(properties))
                .build();
    }

    @Bean
    S3Client s3Client(AwsProperties properties) {
        return S3Client.builder()
                .endpointOverride(URI.create(properties.getEndpointUrl()))
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(credentialsProvider(properties))
                .forcePathStyle(true)
                .build();
    }

    @Bean
    DynamoDbClient dynamoDbClient(AwsProperties properties) {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(properties.getEndpointUrl()))
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(credentialsProvider(properties))
                .build();
    }

    private StaticCredentialsProvider credentialsProvider(AwsProperties properties) {
        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        properties.getAccessKey(),properties.getSecretKey()
                );

        return StaticCredentialsProvider.create(credentials);
    }


}
