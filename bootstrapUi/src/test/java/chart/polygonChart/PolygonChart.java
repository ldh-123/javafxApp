package chart.polygonChart;

import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class PolygonChart extends Region {

    private static final double POINTS_RADIUS = 4;

    private double borders_insets = 10;

    private Color rectBorderFillColor = Color.TRANSPARENT;
    private Color rectBorderStrokeColor = Color.BLACK;
    private Color radarStrokeColor = Color.BLACK;

    private Rectangle rectBorder;

    private double WIDTH;
    private double HEIGHT;
    private double radarStrokeWidth = 1.0f;
    private double xCenter;
    private double yCenter;
    private double radius;
    private double maxValue;
    private double minValue;

    private String categoriesName[];
    private Line[] linesOfPoints;

    private int numberOfShapes;
    private boolean isAnimated = true;

    private ArrayList<Circle> allCircle = new ArrayList<>();
    private ArrayList<Text> allCircleTexts = new ArrayList<>();
    private ArrayList<ChartValue> allValues = new ArrayList<>();

    private ScaleTransition polygonTransitioAnimation;
    private Color defaultColors[];

    public PolygonChart(int width, int height, int numberOfShapes, String categoriesNames[], double minValue,
                        double maxValue) {

        this.numberOfShapes = numberOfShapes;
        this.maxValue = maxValue;
        this.minValue = minValue;

        this.categoriesName = categoriesNames;
        this.setStyle("-fx-background-color:white");

        this.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
                                Number newSceneHeight) {
                HEIGHT = newSceneHeight.doubleValue();
                updateChart();
            }

        });

        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
                                Number newSceneWidth) {
                WIDTH = newSceneWidth.doubleValue();
                updateChart();
            }
        });

        this.setPrefSize(width + borders_insets, height + borders_insets);

        setBorders();

        setRadarShape();

        setRadarValuesLabels();

        updateValues();
    }

    private void setBorders() {

        rectBorder = new Rectangle(borders_insets, borders_insets, WIDTH - borders_insets * 2,
                HEIGHT - borders_insets * 2);
        rectBorder.setFill(rectBorderFillColor);
        rectBorder.setStroke(rectBorderStrokeColor);

        getChildren().add(rectBorder);

    }

    private void setRadarShape() {

        ArrayList<double[]> shapeCordsList = new ArrayList<>();

        for (int i = 0; i < numberOfShapes; i++) {
            shapeCordsList.add(new double[categoriesName.length * 2]);
        }

        double angleOfPoints = 360.0f / categoriesName.length;

        xCenter = (rectBorder.getX() + rectBorder.getWidth()) / 2;
        yCenter = (rectBorder.getY() + rectBorder.getHeight()) / 2;

        double angle = -90;

        for (int i = 0; i < categoriesName.length * 2; i = i + 2) {

            double rad = angle * Math.PI / 180.0f;

            for (int j = 0; j < shapeCordsList.size(); j++) {
                shapeCordsList.get(j)[i] = xCenter + (radius - radius / (numberOfShapes * 1.0) * j) * Math.cos(rad);
                shapeCordsList.get(j)[i + 1] = yCenter + (radius - radius / (numberOfShapes * 1.0) * j) * Math.sin(rad);
            }

            angle += angleOfPoints;
        }

        ArrayList<Polygon> radarShapeList = new ArrayList<>();

        for (int i = 0; i < numberOfShapes; i++) {
            Polygon currentPolygon = new Polygon(shapeCordsList.get(i));

            if (i % 2 == 0) {
                currentPolygon.setFill(Color.rgb(200, 200, 200, 0.6));
            } else {
                currentPolygon.setFill(Color.rgb(250, 250, 250, 0.6));
            }

            currentPolygon.setStroke(radarStrokeColor);
            currentPolygon.setStrokeWidth(radarStrokeWidth);

            radarShapeList.add(currentPolygon);

        }

        getChildren().addAll(radarShapeList);

        linesOfPoints = new Line[categoriesName.length];

        int index = 0;
        for (int i = 0; i < categoriesName.length; i++) {
            Line currentLine = new Line(xCenter, yCenter, shapeCordsList.get(0)[index],
                    shapeCordsList.get(0)[index + 1]);

            currentLine.setStroke(radarStrokeColor);
            currentLine.setStrokeWidth(radarStrokeWidth);

            linesOfPoints[i] = currentLine;

            getChildren().add(currentLine);
            index = index + 2;
        }

        createLinesAndCategories(shapeCordsList);

    }

    private void setRadarValuesLabels() {

        Text startValueText = new Text(xCenter - 20, yCenter, String.valueOf(minValue));
        startValueText.setFill(Color.RED);
        startValueText.setFont(Font.font(15));

        Text middleValueText = new Text(xCenter - 20, yCenter - radius / 2, String.valueOf(maxValue / 2));
        middleValueText.setFill(Color.RED);
        middleValueText.setFont(Font.font(15));

        Text maxValueText = new Text(xCenter - 20, yCenter - radius, String.valueOf(maxValue));
        maxValueText.setFill(Color.RED);
        maxValueText.setFont(Font.font(15));

        getChildren().addAll(startValueText, middleValueText, maxValueText);
    }

    @SuppressWarnings("deprecation")
    private void createLinesAndCategories(ArrayList<double[]> shapeCordsList) {
        int index = 0;

        for (int i = 0; i < categoriesName.length; i++) {
            double xPlus = 0;
            double yPlus = 0;

            double x = shapeCordsList.get(0)[index];
            double y = shapeCordsList.get(0)[index + 1];

            HBox pane = new HBox();

            Label categoryLabel = new Label(categoriesName[i]);
            categoryLabel.setStyle("-fx-text-fill:black");
            categoryLabel.setFont(Font.font(15));

            pane.getChildren().add(categoryLabel);

            @SuppressWarnings("unused")
            Scene s = new Scene(pane);

            categoryLabel.impl_processCSS(true);

            double labelWidth = categoryLabel.prefWidth(-1);
            double labelHeight = categoryLabel.prefHeight(-1);

            pane.getChildren().clear();

            if (x < xCenter) {
                xPlus = -labelWidth;
            }

            if (y < yCenter) {
                yPlus = -labelHeight;
            }
            categoryLabel.setLayoutX(x + xPlus);
            categoryLabel.setLayoutY(y + yPlus);

            getChildren().add(categoryLabel);
            index = index + 2;
        }

    }

    public void setValues(double[] values) {

        double max = getMaxValue(values);

        if (max > maxValue) {
            setMaxCategoryValue(max);
        }

        ChartValue radValues[] = new ChartValue[categoriesName.length];

        for (int i = 0; i < categoriesName.length; i++) {
            radValues[i] = new ChartValue(categoriesName[i], values[i]);

            Circle circle = new Circle();
            Text text = new Text();

            allCircle.add(circle);
            allCircleTexts.add(text);
            allValues.add(radValues[i]);
        }

        updateChart();

    }

    private void drawValue(ChartValue radObject, Circle circle, Text text) {
        String category = radObject.getCategory();
        double value = radObject.getValue();
        int index = -1;

        double angleOfPoints = 360.0f / categoriesName.length;
        double angle = -90;

        for (int i = 0; i < categoriesName.length; i++) {
            if (categoriesName[i].equals(category)) {
                index = i;
                break;
            }
            angle += angleOfPoints;
        }

        if (index == -1 || linesOfPoints == null || linesOfPoints.length == 0) {
            return;
        }

        if (value >= minValue && value <= maxValue) {
            double pos = value / maxValue;
            double startX = linesOfPoints[index].getStartX();
            double startY = linesOfPoints[index].getStartY();

            double rad = angle * Math.PI / 180.0f;

            double x = startX + (radius * pos) * Math.cos(rad);
            double y = startY + (radius * pos) * Math.sin(rad);

            radObject.setX(x);
            radObject.setY(y);

            circle.setCenterX(x);
            circle.setCenterY(y);
            circle.setRadius(POINTS_RADIUS);
            circle.setFill(Color.RED);

            text.setX(x + 5);
            text.setY(y);
            text.setText(String.valueOf(value));
            text.setFont(Font.font(15));

            getChildren().addAll(circle, text);
        }

    }

    private void setRadius() {
        if (WIDTH > HEIGHT) {
            radius = HEIGHT / 2 - borders_insets * 5;
        } else {
            radius = WIDTH / 2 - borders_insets * 5;
        }
    }

    public void removeValue(int index) {
        this.getChildren().remove(allCircle.remove(index));
        this.getChildren().remove(allCircleTexts.remove(index));

        allValues.remove(index);
    }

    public void updateValues() {

        if (allValues.isEmpty()) {
            return;
        }

        for (int i = 0; i < allValues.size(); i++) {
            drawValue(allValues.get(i), allCircle.get(i), allCircleTexts.get(i));
        }

        drawPolygon();
    }

    public void clearValues() {
        getChildren().removeAll(allCircle);
        getChildren().removeAll(allCircleTexts);

        allValues.clear();
        allCircle.clear();
        allCircleTexts.clear();
    }

    private void clearCanvas() {
        getChildren().clear();
    }

    public void setMaxCategoryValue(double value) {
        maxValue = value;
        clearCanvas();

        setBorders();
        setRadarShape();
        setRadarValuesLabels();
    }

    public void updateChart() {
        clearCanvas();
        setRadius();
        setBorders();
        setRadarShape();
        setRadarValuesLabels();
        updateValues();
    }

    private void drawPolygon() {

        int categLength = categoriesName.length;

        for (int j = 0; j < allCircle.size() / categLength; j++) {

            double points[] = new double[categLength * 2];
            int index = 0;

            for (int i = 0; i < categLength; i++) {
                points[index] = allCircle.get(i + categLength * j).getCenterX();
                points[index + 1] = allCircle.get(i + categLength * j).getCenterY();
                index = index + 2;
            }

            Polygon polygon = new Polygon(points);
            polygon.setStroke(Color.TRANSPARENT);

            polygon.setFill(getPolygonColor(j));
            polygon.setStrokeWidth(2);

            if (isAnimated) {
                polygonTransitioAnimation = new ScaleTransition(Duration.millis(700), polygon);

                polygonTransitioAnimation.setFromX(0);
                polygonTransitioAnimation.setFromY(0);
                polygonTransitioAnimation.setByX(1.0f);
                polygonTransitioAnimation.setByY(1.0f);

                polygonTransitioAnimation.play();
            }

            getChildren().add(polygon);

        }

    }

    private Paint getPolygonColor(int j) {

        if (defaultColors == null) {
            defaultColors = new Color[] {
                    Color.rgb(0, 0, 255, 0.8),
                    Color.rgb(255, 100, 70, 0.7),
                    Color.rgb(70, 255, 255, 0.65),
                    Color.rgb(255, 70, 255, 0.6),
                    Color.rgb(100, 70, 100, 0.5),
            };
        }

        if (j > defaultColors.length) {
            Random rand = new Random();
            return Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 0.5);
        }

        // TODO Auto-generated method stub
        return defaultColors[j];
    }

    private double getMaxValue(double[] values) {
        double max = values[0];

        for (int i = 1; i < values.length; i++) {
            if (max < values[i]) {
                max = values[i];
            }
        }

        return max;
    }

    public void setAnimation(boolean isAnimated) {
        this.isAnimated = isAnimated;
    }
}
