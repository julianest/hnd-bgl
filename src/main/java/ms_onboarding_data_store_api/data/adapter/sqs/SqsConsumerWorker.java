package ms_onboarding_data_store_api.data.adapter.sqs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms_onboarding_data_store_api.domain.dto.event.OnboardingEvent;
import ms_onboarding_data_store_api.domain.dto.event.OnboardingEventDetail;
import ms_onboarding_data_store_api.domain.service.OnboardingService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqsConsumerWorker {

    private final SqsConsumerAdapter sqsConsumerAdapter;
    private final EventDeserializer eventDeserializer;
    private final OnboardingService onboardingService;

    public void start() {
        log.info("Iniciando consumidor SQS...");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sqsConsumerAdapter.receiveMessages()
                        .forEach(message -> {
                            log.info("Procesando mensaje SQS. messageId={}",message.messageId());
                            OnboardingEvent event = eventDeserializer.deserialize(message.body());
                            OnboardingEventDetail eventDetail = event.getDetail();
                            log.info(
                                    "Evento recibido. eventId={}, sessionId={}, customerId={}",
                                    eventDetail.getEventId(), eventDetail.getSessionId(), eventDetail.getCustomerId()
                            );
                            onboardingService.process(event, message.body());
                            sqsConsumerAdapter.deleteMessage(message);
                        });

            } catch (Exception exception) {
                log.error("Error consumiendo mensajes desde SQS",exception);
            }
        }
        log.info("Consumidor SQS detenido.");
    }
}
