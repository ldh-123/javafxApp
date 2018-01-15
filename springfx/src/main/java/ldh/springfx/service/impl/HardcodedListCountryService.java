package ldh.springfx.service.impl;

import ldh.springfx.model.Country;
import ldh.springfx.service.CountryService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class HardcodedListCountryService implements CountryService {
    @Override
    public Set<Country> getAllCountries() {
        Set<Country> result = new HashSet<>();
        result.add(new Country("AU", "Australia"));
        result.add(new Country("BR", "Brazil"));
        result.add(new Country("BE", "Belgium"));
        return result;
    }
}
