package bg.softuni.exercisemore.service;

import bg.softuni.exercisemore.entities.Album;

public interface AlbumService {
    void seed();

    Album getRandom();
}
