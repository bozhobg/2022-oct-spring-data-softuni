package bg.softuni.exercisemore.service.impl;

import bg.softuni.exercisemore.constants.ExceptionMessageTemplates;
import bg.softuni.exercisemore.entities.Album;
import bg.softuni.exercisemore.exception.EntityNotFoundForIdException;
import bg.softuni.exercisemore.repository.AlbumRepository;
import bg.softuni.exercisemore.service.AlbumService;
import bg.softuni.exercisemore.service.PictureService;
import bg.softuni.exercisemore.service.UserService;
import bg.softuni.exercisemore.util.GenerationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final static String TEMPLATE = "album";
    private final static int INIT_COUNT = 10;

    private final AlbumRepository albumRepository;

    private final UserService userService;
    private final PictureService pictureService;

    @Autowired
    public AlbumServiceImpl(
            AlbumRepository albumRepository,
            UserService userService,
            PictureService pictureService
    ) {
        this.albumRepository = albumRepository;
        this.userService = userService;
        this.pictureService = pictureService;
    }

    @Override
    public void seed() {
        if (this.albumRepository.count() > 0) return;

        for (int i = 1; i <= INIT_COUNT; i++) {

            Album entity = this.albumRepository.save(
                    new Album().setName(TEMPLATE + i)
                            .setBackgroundColor(TEMPLATE + i + "BackgroundColor")
                            .setPublic(GenerationUtil.RANDOM.nextBoolean())
                            .setOwner(this.userService.getRandom())
            );
        }
    }

    @Override
    public Album getRandom() {
        int index = GenerationUtil.RANDOM.nextInt((int) this.albumRepository.count()) + 1;

        return this.albumRepository.findById(index)
                .orElseThrow(() -> new EntityNotFoundForIdException(
                        String.format(ExceptionMessageTemplates.ALBUM_NOT_FOUND_FOR_ID, index)
                ));
    }
}
