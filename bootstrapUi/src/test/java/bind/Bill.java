package bind;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;

/**
 * Created by ldh on 2018/5/8.
 */
public class Bill {

    // Define the property

    private DoubleProperty amountDue = new SimpleDoubleProperty();

    // Define a getter for the property's value

    public final double getAmountDue(){return amountDue.get();}

    // Define a setter for the property's value

    public final void setAmountDue(double value){amountDue.set(value);}

    // Define a getter for the property itself

    public DoubleProperty amountDueProperty() {return amountDue;}

    public static void main(String[] args) {
        Bill bill1 = new Bill();
        Bill bill2 = new Bill();
        Bill bill3 = new Bill();

        NumberBinding total = Bindings.add(bill1.amountDueProperty().add(bill2.amountDueProperty()),
                bill3.amountDueProperty());
        total.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("The binding is now invalid.");
            }
        });
//        total.addListener((b, o, n)->{
//            System.out.println("change");
//            if (n!= null) {
//                System.out.println("new:" + n);
//            }
//        });

        //第一次调用使绑定失效
        bill1.setAmountDue(200.00);
        //绑定现在无效
        bill2.setAmountDue(100.00);
        bill3.setAmountDue(75.00);
        //绑定现在有效
        System.out.println(total.getValue());
        //再次使用失效
        bill2.setAmountDue(150.00);
        bill3.setAmountDue(150.00);
        //使它有效
        System.out.println(total.getValue());
    }
}
