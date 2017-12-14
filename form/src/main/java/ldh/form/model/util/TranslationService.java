package ldh.form.model.util;


import java.util.ArrayList;
import java.util.List;

public abstract class TranslationService {

    private List<Runnable> listeners = new ArrayList<>();

    public abstract String translate(String key);

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public void removeListener(Runnable listener) {
        listeners.remove(listener);
    }

    protected void notifyListeners() {
        listeners.forEach(Runnable::run);
    }

}
