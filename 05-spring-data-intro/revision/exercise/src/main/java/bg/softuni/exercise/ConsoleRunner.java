package bg.softuni.exercise;

import bg.softuni.exercise.service.AuthorService;
import bg.softuni.exercise.service.BookService;
import bg.softuni.exercise.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public ConsoleRunner(
            SeedService seedService,
            BookService bookService,
            AuthorService authorService
    ) {
        this.seedService = seedService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedService.seedAll();
//        this.bookService.printAllBooksAfter(LocalDate.of(2000, 1,1));
//        this.authorService.printAuthorsWithBooksBefore(LocalDate.of(1990, 1, 1));
//        this.authorService.printAllAuthorsOrderedByBooksCount();
        this.bookService.findAllByAuthorNamesAndSortByReleaseDateAndTitle("George", "Powell");
    }
}
