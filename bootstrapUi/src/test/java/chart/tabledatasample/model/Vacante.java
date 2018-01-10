/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chart.tabledatasample.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author afelipelc 
 * En esta clase Modelo se utilizan Observable properties Ver
 * http://docs.oracle.com/javafx/2/api/javafx/beans/property/package-summary.html
 */
public class Vacante {

    private StringProperty empresa;
    private StringProperty especialidad;
    private FloatProperty salario;
    
    //constructor simple
    public Vacante() {
    }

    //constructor con 3 parámetros
    public Vacante(String Empresa, String Especialidad, float Salario) {
        this.empresa = new SimpleStringProperty(Empresa);
        this.especialidad = new SimpleStringProperty(Especialidad);
        this.salario = new SimpleFloatProperty(Salario);
    }

    //Métodos de escritura para las propiedades de Vacante
    public void setEmpresa(String Empresa) {
        this.empresa.set(Empresa);
    }

    public void setEspecialidad(String Especialidad) {
        this.especialidad.set(Especialidad);
    }

    public void setSalario(Float Salario) {
        this.salario.set(Salario);
    }

    //Get Observable Properties
    public StringProperty empresaProperty() {
        return this.empresa;
    }

    public StringProperty especialidadProperty() {
        return this.especialidad;
    }

    public FloatProperty salarioProperty() {
        return this.salario;
    }
}
