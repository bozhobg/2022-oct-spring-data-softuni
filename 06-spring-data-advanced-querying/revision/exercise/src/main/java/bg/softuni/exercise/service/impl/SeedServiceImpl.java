package bg.softuni.exercise.service.impl;

import bg.softuni.exercise.domain.entities.Author;
import bg.softuni.exercise.domain.entities.Book;
import bg.softuni.exercise.domain.entities.Category;
import bg.softuni.exercise.domain.entities.enums.AgeRestrictionEnum;
import bg.softuni.exercise.domain.entities.enums.EditionEnum;
import bg.softuni.exercise.service.AuthorService;
import bg.softuni.exercise.service.BookService;
import bg.softuni.exercise.service.CategoryService;
import bg.softuni.exercise.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {

    private static final String RESOURCE_PATH = "src/main/resources/";
    private static final String BOOKS = "books.txt";
    private static final String AUTHORS = "authors.txt";
    private static final String CATEGORIES = "categories.txt";

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public SeedServiceImpl(
            AuthorService authorService,
            CategoryService categoryService,
            BookService bookService
    ) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryService.isDataSeeded()) return;

        List<Category> categories = Files.readAllLines(Path.of(RESOURCE_PATH + CATEGORIES))
                .stream()
                .filter(l -> !l.isBlank())
                .map(c -> new Category().setName(c))
                .collect(Collectors.toList());

        this.categoryService.seedCategories(categories);
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorService.isDataSeeded()) return;

        List<Author> authors = Files.readAllLines(Path.of(RESOURCE_PATH + AUTHORS)).stream()
                .map(l -> {
                    String[] data = l.split("\\s+");
                    return new Author()
                            .setFirstName(data[0])
                            .setLastName(data[1]);
                })
                .collect(Collectors.toList());

        this.authorService.seedAuthors(authors);
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookService.isDataSeeded()) return;

        List<Book> books = new ArrayList<>();

        for (String line : Files.readAllLines(Path.of(RESOURCE_PATH + BOOKS))) {
            String[] splitLine = line.split("\\s+");

            int editionOrdinal = Integer.parseInt(splitLine[0]);
            EditionEnum edition = EditionEnum.values()[editionOrdinal];
            LocalDate releaseDate = parseDate(splitLine[1]);
            int copies = Integer.parseInt(splitLine[2]);
            BigDecimal price = new BigDecimal(splitLine[3]);
            AgeRestrictionEnum ageRestriction = AgeRestrictionEnum.values()[Integer.parseInt(splitLine[4])];
            String title = Arrays.stream(splitLine)
                    .skip(5)
                    .collect(Collectors.joining(" "));
            Author randomAuthor = this.authorService.getRandomAuthor();
            Set<Category> randomCategories = this.categoryService.getRandomCategory();

            Book book = new Book()
                    .setEditionType(edition)
                    .setReleaseDate(releaseDate)
                    .setCopies(copies)
                    .setPrice(price)
                    .setAgeRestriction(ageRestriction)
                    .setTitle(title)
                    .setAuthor(randomAuthor)
                    .setCategories(randomCategories);

            books.add(book);
        }

        this.bookService.seedBooks(books);
    }

    private static LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return LocalDate.parse(dateString, formatter);
    }
}
