package ldh.fx.component.function;

import javafx.util.StringConverter;
import ldh.common.mybatis.ValuedEnum;

/**
 * Created by ldh on 2017/4/23.
 */
public class ValuedEnumStringConverter extends StringConverter<ValuedEnum> {

    @Override
    public String toString(ValuedEnum object) {
        if (object == null) return "";
        return object.getDesc();
    }

    @Override
    public ValuedEnum fromString(String str) {
        if (str == null || str.equals("")) return null;
        return null;
    }
}
