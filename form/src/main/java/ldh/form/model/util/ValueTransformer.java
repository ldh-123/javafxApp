package ldh.form.model.util;

public interface ValueTransformer<T> {

    T transform(String input);

}
