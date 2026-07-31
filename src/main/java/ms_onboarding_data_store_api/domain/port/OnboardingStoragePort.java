package ms_onboarding_data_store_api.domain.port;

public interface OnboardingStoragePort {

    void save(String key, String content);
}
