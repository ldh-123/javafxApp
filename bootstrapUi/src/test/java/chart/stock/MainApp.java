package chart.stock;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        CandleStickChart candleStickChart = new CandleStickChart("S&P 500 Index", buildBars());
        Scene scene = new Scene(candleStickChart);
        scene.getStylesheets().add("/css/CandleStickChartStyles.css");

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();

        candleStickChart.setYAxisFormatter(new DecimalAxisFormatter("#000.00"));
    }


    public List<BarData> buildBars() {
        double previousClose = 1850;


        final List<BarData> bars = new ArrayList<>();
        GregorianCalendar now = new GregorianCalendar();
        for (int i = 0; i < 26; i++) {
            double open = getNewValue(previousClose);
            double close = getNewValue(open);
            double high = Math.max(open + getRandom(),close);
            double low = Math.min(open - getRandom(),close);
            previousClose = close;

            BarData bar = new BarData((GregorianCalendar) now.clone(), open, high, low, close, 1);
            now.add(Calendar.MINUTE, 5);
            bars.add(bar);
        }
        return bars;
    }


    protected double getNewValue( double previousValue ) {
        int sign;

        if( Math.random() < 0.5 ) {
            sign = -1;
        } else {
            sign = 1;
        }
        return getRandom() * sign + previousValue;
    }

    protected double getRandom() {
        double newValue = 0;
        newValue = Math.random() * 10;
        return newValue;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
