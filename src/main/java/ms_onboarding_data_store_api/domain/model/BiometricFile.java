package ms_onboarding_data_store_api.domain.model;

public record BiometricFile(
        String sessionId,
        BiometricFileType fileType,
        String base64,
        String contentType
) {}
