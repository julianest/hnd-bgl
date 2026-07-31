package ms_onboarding_data_store_api.data.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
@Getter
@Setter
public class AwsProperties {
    private String region;
    private String endpointUrl;
    private String accessKey;
    private String secretKey;

    private Sqs sqs;
    private S3 s3;
    private Dynamo dynamodb;
    private EventBridge eventbridge;

    @Getter
    @Setter
    public static class Sqs {
        private String queueUrl;
    }

    @Getter
    @Setter
    public static class S3 {
        private String bucket;
    }

    @Getter
    @Setter
    public static class Dynamo {
        private String table;
    }

    @Getter
    @Setter
    public static class EventBridge {
        private String busName;
    }
}
