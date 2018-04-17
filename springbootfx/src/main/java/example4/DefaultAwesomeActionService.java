package example4;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class DefaultAwesomeActionService implements AwesomeActionService {

    private ResourceBundle bundle = ResourceBundle.getBundle("example4.helloworld");

    @Override
    public String processName(final String name) {
        return MessageFormat.format(bundle.getString("greeting"), name);
    }

}
