package ms_onboarding_data_store_api.data.adapter.dynamodb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms_onboarding_data_store_api.data.config.AwsProperties;
import ms_onboarding_data_store_api.domain.port.IdempotencyPort;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamoDbIdempotencyAdapter implements IdempotencyPort {

    private final DynamoDbClient dynamoDbClient;
    private final AwsProperties awsProperties;

    @Override
    public boolean registerEvent(String dedupKey) {

        PutItemRequest request = PutItemRequest.builder()
                .tableName(awsProperties.getDynamodb().getTable())
                .item(Map.of(
                        "dedupKey",
                        AttributeValue.builder()
                                .s(dedupKey)
                                .build()
                ))
                .conditionExpression("attribute_not_exists(dedupKey)")
                .build();

        try {
            dynamoDbClient.putItem(request);
            log.info("Evento registrado para idempotencia. dedupKey={}",dedupKey);
            return true;

        } catch (ConditionalCheckFailedException exception) {
            log.info("Evento duplicado detectado. dedupKey={}",dedupKey);
            return false;
        }
    }
}