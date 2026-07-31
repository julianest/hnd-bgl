package ms_onboarding_data_store_api.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms_onboarding_data_store_api.domain.dto.event.OnboardingEvent;
import ms_onboarding_data_store_api.domain.port.IdempotencyPort;
import ms_onboarding_data_store_api.domain.port.OnboardingStoragePort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final IdempotencyPort idempotencyPort;
    private final OnboardingStoragePort onboardingStoragePort;

    public boolean process(OnboardingEvent event, String rawEvent) {

        String eventId = event.getDetail().getEventId();
        String sessionId = event.getDetail().getSessionId();
        String dedupKey = sessionId + "#" + eventId;
        log.info("Procesando evento onboarding. eventId={}, sessionId={}, dedupKey={}",eventId,sessionId,dedupKey);

        boolean newEvent = idempotencyPort.registerEvent(dedupKey);

        if (!newEvent) {
            log.info("Evento ya procesado anteriormente. eventId={}",eventId);
            return false;
        }
        log.info("Evento nuevo aceptado para procesamiento. eventId={}",eventId);
        String s3Key = "onboarding/" + sessionId + "/" + eventId + ".json";

        onboardingStoragePort.save(s3Key,rawEvent);
        return true;
    }
}
