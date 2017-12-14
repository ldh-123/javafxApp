package ldh.form.model.validator;

public class ValidationResult {

    private boolean result;
    private String errorMessage;

    ValidationResult(boolean result, String errorMessage) {
        this.result = result;

        this.errorMessage = !result ? errorMessage : null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean getResult() {
        return result;
    }

}
