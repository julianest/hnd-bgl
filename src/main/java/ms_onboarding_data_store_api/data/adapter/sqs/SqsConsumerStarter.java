package ms_onboarding_data_store_api.data.adapter.sqs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SqsConsumerStarter {

    private final SqsConsumerWorker sqsConsumerWorker;

    @Qualifier("sqsTaskExecutor")
    private final TaskExecutor sqsTaskExecutor;

    @EventListener(ApplicationReadyEvent.class)
    public void startConsumer() {

        sqsTaskExecutor.execute(
                sqsConsumerWorker::start
        );
    }
}