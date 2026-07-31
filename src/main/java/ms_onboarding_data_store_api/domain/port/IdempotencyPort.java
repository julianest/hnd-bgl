package ms_onboarding_data_store_api.domain.port;

public interface IdempotencyPort {

    boolean registerEvent(String dedupKey);
}
