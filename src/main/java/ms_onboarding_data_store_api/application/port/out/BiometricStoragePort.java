package ms_onboarding_data_store_api.application.port.out;

import ms_onboarding_data_store_api.domain.model.BiometricFile;

public interface BiometricStoragePort {
    String store(BiometricFile file);
}
