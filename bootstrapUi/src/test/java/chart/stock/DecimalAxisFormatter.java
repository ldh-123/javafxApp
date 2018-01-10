package chart.stock;

import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by xiongfei.lei on 2017/9/29.
 */
public class DecimalAxisFormatter extends StringConverter<Number> {

    protected DecimalFormat decimalFormat;

    public DecimalAxisFormatter( String format ) {
        decimalFormat = new DecimalFormat(format);
    }

    public DecimalAxisFormatter( DecimalFormat decimalFormat ) {
        this.decimalFormat = decimalFormat;
    }


    @Override
    public String toString(Number object) {
        return decimalFormat.format(object.doubleValue());
    }

    @Override
    public Number fromString(String string) {
        try {
            return decimalFormat.parse(string);
        } catch (ParseException ex) {
            throw new IllegalStateException(ex);
        }
    }



}
