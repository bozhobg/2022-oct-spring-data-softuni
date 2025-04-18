package bg.softuni.exercisemore.service;

import bg.softuni.exercisemore.entities.Town;

public interface TownService {

    Town persist(Town town);

    void seed();

    Town getRandom();
}
