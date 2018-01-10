/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart.tabledatasample;

import chart.tabledatasample.model.Vacante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author afelipelc
 */
public class ChartWindowController implements Initializable {

    @FXML
    BarChart graficaChart;
    @FXML
    CategoryAxis categoriasChartAxis;
    @FXML
    NumberAxis valoresChartAxis;
    
    private ObservableList<String> nombresVacantes = FXCollections.observableArrayList();
    private ObservableList<BarChart.Data> datosChart = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        valoresChartAxis.setLabel("Salarios");
        
    }    
    
    public void setDatos(ObservableList<Vacante> Vacantes)
    {
        for(Vacante item : Vacantes)
        {
            nombresVacantes.add(item.especialidadProperty().get());
            datosChart.add(new BarChart.Data(item.especialidadProperty().get(), item.salarioProperty().get()));
        }
        
        ObservableList<BarChart.Series> barChartData = FXCollections.observableArrayList(
            new BarChart.Series("Vacantes", datosChart));

         categoriasChartAxis.setCategories(nombresVacantes);
        
         graficaChart.setData(barChartData);
         graficaChart.setTitle("Especialidades y Salarios");
    }
}
