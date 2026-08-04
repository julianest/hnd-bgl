package ms_onboarding_data_store_api.domain.model;

import lombok.Getter;

@Getter
public enum BiometricFileType {
    DOCUMENT_FRONT("document-front"),
    DOCUMENT_BACK("document-back"),
    SELFIE("selfie"),
    TEMPLATE("template");

    private final String fileName;

    BiometricFileType(String fileName) {
        this.fileName = fileName;
    }

}
