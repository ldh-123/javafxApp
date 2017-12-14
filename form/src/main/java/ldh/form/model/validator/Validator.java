package ldh.form.model.validator;


public interface Validator<T> {

    ValidationResult validate(T input);

}
