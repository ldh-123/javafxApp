package chart;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;
import java.util.Random;

public class EffectTest extends Application {

    StackPane root;
    private static int MAX_DATA_POINTS = 20;
    private static int Y_DATA_RANGE = 10;
    private static int TICK_UNIT = 10;

    private static int UPDATE_INTERVAL_MS = 1000;
    private LineChart.Series<Number, Number> series1;
    private LineChart<Number, Number> lineChart;
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();

    private SequentialTransition animation;
    private Paint paintXticklabel;
    private double nextX = 0;

    Random rnd = new Random();
    double currentMemBAK=0;
    public EffectTest() {

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(UPDATE_INTERVAL_MS*3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // update chart data
                // note that we addNewStage one data point and remove one data point in this simple example.
                // in a production environment you'd have to addNewStage multiple and remove multiple data points

                double currentMem= Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
                double drawy =(currentMem-currentMemBAK)/1000000;
                currentMemBAK=currentMem;
                System.out.println("currentMem:"+currentMem);
                // addNewStage new points
                //series1.getData().addNewStage(new XYChart.Data<Number, Number>(nextX, Math.cos(Math.toRadians(nextX)) * Y_DATA_RANGE));

                //mem
                series1.getData().add(new XYChart.Data<Number, Number>(nextX, drawy+10));

                //cpu
                //series1.getData().addNewStage(new XYChart.Data<Number, Number>(nextX, getCpuRatioForWindows()));


                // remove points that shouldn't be visible anymore
                if (series1.getData().size() > MAX_DATA_POINTS) {
                    series1.getData().remove(0);

                }
                System.out.println("node size:"+series1.getData().size());

                nextX += 1;

                // update using series 1 as reference
                // series 2 contains same amount of data; if it doesn't for your case,
                // you need to adapt this here and calculate the proper range
                List<XYChart.Data<Number, Number>> data = series1.getData();
                xAxis.setLowerBound(data.get(0).getXValue().doubleValue());
                xAxis.setUpperBound(data.get(data.size() - 1).getXValue().doubleValue());

                ////////
                lineChart.getXAxis().setTickLabelFill(paintXticklabel);

                // xAxis.setTickUnit(1);
                root.setTranslateX(-20);

            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        animation = new SequentialTransition();
        animation.getChildren().addAll(timeline);
    }

    public Parent createContent() {

        xAxis = new NumberAxis();
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        xAxis=new NumberAxis(0, Y_DATA_RANGE+90, TICK_UNIT/10);

        // set Axis property
        // final NumberAxis yAxis = new NumberAxis(1, 21, 0.1);
        // xAxis.setTickUnit(1);
        // xAxis.setPrefWidth(35);
        // xAxis.setMinorTickCount(10);
        // xAxis.setSide(Side.RIGHT);
        // xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
        // @Override
        // public String toString(Number object) {
        // String label;
        // label = String.format("%7.2f", object.floatValue());
        // return label;
        // }
        // });


        //yAxis = new NumberAxis(-Y_DATA_RANGE, Y_DATA_RANGE, TICK_UNIT);
        yAxis = new NumberAxis(0, Y_DATA_RANGE+90, TICK_UNIT/10);
        yAxis.setAutoRanging(false);

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setAnimated(false);
        lineChart.setLegendVisible(false);

        //set if dont want symbols on the point
        lineChart.setCreateSymbols(false);
        series1 = new LineChart.Series<>();
        // series1.getData().addNewStage(new LineChart.Data<Number, Number>(0d, 0d));
        lineChart.getData().add(series1);

        //save ticklabe
        paintXticklabel=xAxis.getTickLabelFill();
        return lineChart;
    }

    public void play() {
        animation.play();
    }

    @Override
    public void stop() {
        animation.pause();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Drawing Operations Test");

        root = new StackPane();
        root.getChildren().add(createContent());

        Scene s= new Scene(root);
        primaryStage.setScene(s);
        primaryStage.show();

        play();
    }

    public static void main(String[] args) {
        launch(args);
        // getCpuRatioForWindows() ;
        System.out.println(getCpuRatioForWindows());

    }

    private static final int CPUTIME = 500;
    private static final int PERCENT = 100;

    public static double getCpuRatioForWindows() {
        try {
            String procCmd =
                    System.getenv("windir")
                            + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";

            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPUTIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime)).intValue() ;
            } else {
                return 0 ;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0 ;
        }
    }
    private static final int FAULTLENGTH = 10;
    private static long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                //Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = substring(line, capidx, cmdidx - 1).trim();
                String cmd = substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                String s1 = substring(line, kmtidx, rocidx - 1).trim();
                String s2 = substring(line, umtidx, wocidx - 1).trim();
                if (caption.equals("System Idle Process") || caption.equals("System"))
                {
                    if (s1.length() > 0)
                        idletime += Long.valueOf(s1).longValue();
                    if (s2.length() > 0)
                        idletime += Long.valueOf(s2).longValue();
                    continue;
                }
                if (s1.length() > 0)
                    kneltime += Long.valueOf(s1).longValue();
                if (s2.length() > 0)
                    usertime += Long.valueOf(s2).longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private static String substring(String src, int start_idx, int end_idx)
    {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idx; i <= end_idx; i++)
        {
            tgt += (char)b[i];
        }
        return tgt;
    }
}