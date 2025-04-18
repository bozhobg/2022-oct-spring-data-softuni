package bg.softuni.exercisemore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(nullable = false)
    private boolean isPublic;

    @ManyToMany
    @JoinTable(name = "albums_pictures",
            joinColumns = @JoinColumn(name = "album_id", nullable = false, referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id", nullable = false, referencedColumnName = "id")
    )
    private Set<Picture> pictures;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Album() {
        this.pictures = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Album setName(String name) {
        this.name = name;
        return this;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Album setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Album setPublic(boolean aPublic) {
        isPublic = aPublic;
        return this;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public User getOwner() {
        return owner;
    }

    public Album setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Album album = (Album) object;
        return Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
