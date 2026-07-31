package ms_onboarding_data_store_api.domain.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OnboardingEvent {

    private String version;
    private String id;
    @JsonProperty("detail-type")
    private String detailType;
    private String source;
    private String account;
    private String time;
    private String region;
    private List<String> resources;
    private OnboardingEventDetail detail;
}
