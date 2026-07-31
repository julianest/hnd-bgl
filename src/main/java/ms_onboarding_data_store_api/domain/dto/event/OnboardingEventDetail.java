package ms_onboarding_data_store_api.domain.dto.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnboardingEventDetail {

    private String eventId;
    private String sessionId;
    private String customerId;
}
