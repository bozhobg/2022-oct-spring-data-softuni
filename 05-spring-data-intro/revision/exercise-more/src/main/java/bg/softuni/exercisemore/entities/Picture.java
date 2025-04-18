package bg.softuni.exercisemore.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String caption;

    @Column(nullable = false, unique = true)
    private String path;

    @ManyToMany(
            mappedBy = "pictures",
            cascade = CascadeType.ALL
    )
    private Set<Album> albums;

    public Picture() {
        this.albums = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Picture setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public Picture setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Picture setPath(String path) {
        this.path = path;
        return this;
    }

    public Set<Album> getAlbums() {
        return albums;
    }
}
