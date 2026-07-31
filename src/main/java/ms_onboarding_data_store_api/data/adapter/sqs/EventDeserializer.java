package ms_onboarding_data_store_api.data.adapter.sqs;

import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ms_onboarding_data_store_api.domain.dto.event.OnboardingEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventDeserializer {

    private final ObjectMapper objectMapper;

    public OnboardingEvent deserialize(String body) {
        try {
            return objectMapper.readValue(body,OnboardingEvent.class);
        } catch (Exception exception) {
            throw new IllegalArgumentException("No fue posible deserializar el evento de onboarding",exception);
        }
    }
}
