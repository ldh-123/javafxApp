package chart;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;


public class AutomatedChart extends Application {

    public class Record{
        private SimpleStringProperty fieldCompany;
        private SimpleDoubleProperty fieldValue1;
        private SimpleDoubleProperty fieldValue2;

        Record(String fCompany, double fValue1, double fValue2){
            this.fieldCompany = new SimpleStringProperty(fCompany);
            this.fieldValue1 = new SimpleDoubleProperty(fValue1);
            this.fieldValue2 = new SimpleDoubleProperty(fValue2);
        }



        public String getFieldCompany() {
            return fieldCompany.get();
        }

        public double getFieldValue1() {
            return fieldValue1.get();
        }

        public double getFieldValue2() {
            return fieldValue2.get();
        }

        public void setFieldCompany(String fCompany) {
            fieldCompany.set(fCompany);
        }

        public void setFieldValue1(Double fValue1) {
            fieldValue1.set(fValue1);
        }

        public void setFieldValue2(Double fValue2) {
            fieldValue2.set(fValue2);
        }
    }

    class MyList {


        ObservableList<Record> dataList;
        ObservableList<PieChart.Data> pieChartData1;
        ObservableList<XYChart.Data> xyList2;

        MyList(){
            dataList = FXCollections.observableArrayList();
            pieChartData1 = FXCollections.observableArrayList();
            xyList2 = FXCollections.observableArrayList();
        }

        public void add(Record r){
            dataList.add(r);
            pieChartData1.add(new PieChart.Data(r.getFieldCompany(), r.getFieldValue1()));
            xyList2.add(new XYChart.Data(r.getFieldCompany(), r.getFieldValue2()));
        }

        public void update1(int pos, Double val){
            pieChartData1.set(pos, new PieChart.Data(pieChartData1.get(pos).getName(), val));
        }

        public void update2(int pos, Double val){
            xyList2.set(pos, new XYChart.Data(xyList2.get(pos).getXValue(), val));
        }
    }

    MyList myList;

    private TableView<Record> tableView = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("lacifitra.com");

        //prepare myList
        myList = new MyList();
        myList.add(new Record("Company A", 100, 120));
        myList.add(new Record("Company B", 200, 210));
        myList.add(new Record("Company C", 50, 70));
        myList.add(new Record("Company D", 75, 50));
        myList.add(new Record("Company X", 110, 120));
        myList.add(new Record("Company Y", 300, 200));
        myList.add(new Record("Company Z", 111, 100));

        Group root = new Group();

        tableView.setEditable(true);

        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };

        TableColumn columnCompany = new TableColumn("Companies");
        columnCompany.setCellValueFactory(
                new PropertyValueFactory<Record,String>("fieldCompany"));
        columnCompany.setMinWidth(60);

        TableColumn columnValue1 = new TableColumn("Value 1");
        columnValue1.setCellValueFactory(
                new PropertyValueFactory<Record,Double>("fieldValue1"));
        columnValue1.setMinWidth(60);

        TableColumn columnValue2 = new TableColumn("Value 2");
        columnValue2.setCellValueFactory(
                new PropertyValueFactory<Record,Double>("fieldValue2"));
        columnValue2.setMinWidth(60);


        columnValue1.setCellFactory(cellFactory);
        columnValue1.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Record, Double>>() {

                    @Override public void handle(TableColumn.CellEditEvent<Record, Double> t) {
                        ((Record)t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setFieldValue1(t.getNewValue());

                        int pos = t.getTablePosition().getRow();
                        myList.update1(pos, t.getNewValue());
                    }
                });

        columnValue2.setCellFactory(cellFactory);
        columnValue2.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Record, Double>>() {

                    @Override public void handle(TableColumn.CellEditEvent<Record, Double> t) {
                        ((Record)t.getTableView()
                                .getItems()
                                .get(t.getTablePosition().getRow())).setFieldValue2(t.getNewValue());

                        int pos = t.getTablePosition().getRow();
                        myList.update2(pos, t.getNewValue());
                    }
                });



        List<String> companyLabels = Arrays.asList(
                "Company A",
                "Company B",
                "Company C",
                "Company D",
                "Company X",
                "Company Y",
                "Company Z");



        final PieChart pieChart1 = new PieChart(myList.pieChartData1);
        pieChart1.setPrefWidth(500);
        pieChart1.setTitle("Chart");




        final CategoryAxis xAxis2 = new CategoryAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Companies");
        xAxis2.setCategories(FXCollections.<String> observableArrayList(companyLabels));
        yAxis2.setLabel("Overall Value");
        XYChart.Series XYSeries2 = new XYChart.Series(myList.xyList2);
        XYSeries2.setName("Chart");

        final LineChart<String,Number> lineChart2 =
                new LineChart<>(xAxis2,yAxis2);
        // lineChart2.setStyle(" -fx-stroke: #8b4513; -fx-fill: #8b4513;-fx-fill: #8b4513; -fx-font-size: 1em;  -fx-background-color: transparent;  transparent; -fx-background-image: url('https://t4.ftcdn.net/jpg/01/27/86/59/240_F_127865986_Z4BWZutKbjmNfGYXriCvn81FFPKq9xjY.jpg'); -fx-stroke: #daa520;");
        lineChart2.setTitle("Statistics");
        lineChart2.setPrefWidth(500);

        lineChart2.getData().add(XYSeries2);


        tableView.setItems(myList.dataList);
        tableView.getColumns().addAll(columnCompany, columnValue1, columnValue2);
        tableView.setPrefWidth(300);

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        hBox.getChildren().addAll(tableView, pieChart1, lineChart2);

        root.getChildren().add(hBox);

        primaryStage.setScene(new Scene(root, 1700, 500));

        primaryStage.show();
    }




    class EditingCell extends TableCell<Record, Double> {
        private TextField textField;

        public EditingCell() {}

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }

            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }

                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }


        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(Double.parseDouble(textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}
