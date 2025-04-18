package bg.softuni.exercisemore.service.impl;

import bg.softuni.exercisemore.entities.Album;
import bg.softuni.exercisemore.entities.Picture;
import bg.softuni.exercisemore.repository.AlbumRepository;
import bg.softuni.exercisemore.repository.PictureRepository;
import bg.softuni.exercisemore.service.PictureService;
import bg.softuni.exercisemore.util.GenerationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PictureServiceImpl implements PictureService {

    private static final int INIT_COUNT = 20;
    private static final int ALBUM_COUNT_BOUND = 4;
    private static final String TEMPLATE = "picture";

    private final PictureRepository pictureRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public PictureServiceImpl(
            PictureRepository pictureRepository,
            AlbumRepository albumRepository1
    ) {
        this.pictureRepository = pictureRepository;
        this.albumRepository = albumRepository1;
    }

    @Override
    public void seedPictures() {
        if (this.pictureRepository.count() > 0) return;

//        generate pic -> get albums by single user -> randomly add to >= 1 albums of user
//        need to always save on both sides of the association in order to be consistent
//        @Transactional to not be blocked by Fetch.LAZY of .getAlbums()

        for (int i = 1; i <= INIT_COUNT; i++) {
            String base = TEMPLATE + i;

            Picture picture = new Picture()
                    .setTitle(base + "Title")
                    .setCaption(base + "Caption")
                    .setPath(base + "Path");

            this.pictureRepository.saveAndFlush(picture);
        }
    }

    @Override
    public void seedToAlbums() {
        List<Picture> pictures = this.pictureRepository.findAll();
        List<Album> albums = this.albumRepository.findAll();

        for (int i = 0; i < pictures.size(); i++) {
            Picture currentPicture = pictures.get(i);

            Album album = albums.get(GenerationUtil.RANDOM.nextInt(albums.size()));
            List<Album> userAlbums = album.getOwner().getAlbums().stream().toList();
            int userAlbumsSize = userAlbums.size();

            for (int j = 0; j < userAlbums.size(); j++) {
                if (j != 0 && !GenerationUtil.RANDOM.nextBoolean()) continue;

                Album userAlbum = userAlbums.get(GenerationUtil.RANDOM.nextInt(userAlbumsSize));
                userAlbum.getPictures().add(currentPicture);
                currentPicture.getAlbums().add(userAlbum);

                this.pictureRepository.saveAndFlush(currentPicture);
                this.albumRepository.saveAndFlush(userAlbum);
            }
        }
    }
}
