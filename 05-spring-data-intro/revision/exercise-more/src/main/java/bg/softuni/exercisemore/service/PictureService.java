package bg.softuni.exercisemore.service;

import org.springframework.transaction.annotation.Transactional;

public interface PictureService {

    void seedPictures();


    void seedToAlbums();

    @Transactional
    default void seed() {
        seedPictures();
        seedToAlbums();
    }
}
