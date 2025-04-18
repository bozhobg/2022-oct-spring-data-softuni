package bg.softuni.exercisemore.service;

import bg.softuni.exercisemore.entities.Country;

public interface CountryService {

    Country persist(Country country);

    void seed();

    Country getRandom();
}
