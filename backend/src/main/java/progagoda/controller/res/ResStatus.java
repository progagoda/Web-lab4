package progagoda.controller.res;

public enum ResStatus {
    OK("ok"),
    VALIDATION_FAILED("Validation failed"),
    REQUEST_ERROR("Request error"),
    UNDEFINED_ERROR("Undefined error");

    private final String description;

    private ResStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
