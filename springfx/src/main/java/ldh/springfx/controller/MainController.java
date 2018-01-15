package ldh.springfx.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import ldh.springfx.model.Country;
import ldh.springfx.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainController {
    @FXML
    public ComboBox<Country> countriesComboBox;

    @Autowired
    private CountryService countryService;

    @FXML
    public void initialize() {
        countriesComboBox.setConverter(new CountryNameStringConverter());
        countriesComboBox.setItems(FXCollections.observableArrayList(countryService.getAllCountries()));
    }

    private static class CountryNameStringConverter extends StringConverter<Country> {
        @Override
        public String toString(Country object) {
            return object.getName();
        }

        @Override
        public Country fromString(String string) {
            return null;
        }
    }
}
