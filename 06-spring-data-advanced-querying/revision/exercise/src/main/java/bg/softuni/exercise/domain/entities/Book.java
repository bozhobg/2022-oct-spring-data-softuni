package bg.softuni.exercise.domain.entities;

import bg.softuni.exercise.domain.entities.enums.AgeRestrictionEnum;
import bg.softuni.exercise.domain.entities.enums.EditionEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated
    @Column(name = "edition_type", nullable = false)
    private EditionEnum editionType;

    @Column(columnDefinition = "DECIMAL(19,2)", nullable = false)
    private BigDecimal price;

    @Column(columnDefinition = "INT(11)", nullable = false)
    private Integer copies;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated
    @Column(name = "age_restriction", nullable = false)
    private AgeRestrictionEnum ageRestriction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "author_id")
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id", nullable = false, referencedColumnName = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "category_id"))
    private Set<Category> categories;

    public Book() {
        this.categories = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Book setDescription(String description) {
        this.description = description;
        return this;
    }

    public EditionEnum getEditionType() {
        return editionType;
    }

    public Book setEditionType(EditionEnum editionType) {
        this.editionType = editionType;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Book setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getCopies() {
        return copies;
    }

    public Book setCopies(Integer copies) {
        this.copies = copies;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Book setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public AgeRestrictionEnum getAgeRestriction() {
        return ageRestriction;
    }

    public Book setAgeRestriction(AgeRestrictionEnum ageRestriction) {
        this.ageRestriction = ageRestriction;
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public Book setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Book setCategories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }
}
