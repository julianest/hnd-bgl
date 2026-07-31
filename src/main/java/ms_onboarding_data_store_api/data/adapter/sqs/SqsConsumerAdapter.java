package ms_onboarding_data_store_api.data.adapter.sqs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms_onboarding_data_store_api.data.config.AwsProperties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqsConsumerAdapter {

    private final SqsClient sqsClient;
    private final AwsProperties awsProperties;

    public List<Message> receiveMessages() {

        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(awsProperties.getSqs().getQueueUrl())
                .maxNumberOfMessages(1)
                .waitTimeSeconds(10)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request)
                .messages();

        if (!messages.isEmpty()) {
            log.info("Mensaje(s) recibido(s) desde SQS. count={}", messages.size());
        }

        return messages;
    }

    public void deleteMessage(Message message) {

        DeleteMessageRequest request = DeleteMessageRequest.builder()
                .queueUrl(awsProperties.getSqs().getQueueUrl())
                .receiptHandle(message.receiptHandle())
                .build();

        sqsClient.deleteMessage(request);

        log.info("Mensaje eliminado de SQS. messageId={}",message.messageId());
    }
}
