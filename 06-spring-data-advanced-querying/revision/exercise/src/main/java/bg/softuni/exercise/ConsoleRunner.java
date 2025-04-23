package bg.softuni.exercise;

import bg.softuni.exercise.domain.entities.enums.EditionEnum;
import bg.softuni.exercise.service.AuthorService;
import bg.softuni.exercise.service.BookService;
import bg.softuni.exercise.service.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final static Scanner SC = new Scanner(System.in);

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
//        -> 05 Spring Data Intro
        this.seedService.seedAll();
//        this.bookService.printAllBooksAfter(LocalDate.of(2000, 1,1));
//        this.authorService.printAuthorsWithBooksBefore(LocalDate.of(1990, 1, 1));
//        this.authorService.printAllAuthorsOrderedByBooksCount();
//        this.bookService.findAllByAuthorNamesAndSortByReleaseDateAndTitle("George", "Powell");

//        -> 06 Spring Data Advanced Querying:
//        pr01BookTitlesByAgeRestriction();
//        pr02BookTitlesGoldenBooks();
//        pr03BookTitlesByPrice();
//        pr04NotReleasedBooks();
//        pr05BookReleasedBeforeDate();
//        pr06AuthorsSearch();
//        pr07BookSearch();
//        pr08BookTitleSearch();
//        pr09CountBooks();
//        pr10TotalBookCopies();
//        pr11ReducedBook();
//        pr12IncreaseBookCopies();
//        pr13RemoveBooks();
        pr14StoredProcedure();
    }

    private void pr14StoredProcedure() {
        String firstNameInput = SC.nextLine();
        String lastNameInput = SC.nextLine();
//        TODO: Why in db params 2 in query 3? how is that? -> is it 2 in and 1 out as expected
//        TODO: second approach @NamedStoredProcedureQueries
        System.out.println(this.authorService.callBooksCountAuthorFirstAndLastName(firstNameInput, lastNameInput));
    }

    private void pr13RemoveBooks() {
        Integer copies = Integer.parseInt(SC.nextLine());
        System.out.println(this.bookService.deleteBooksWithCopiesLessThan(copies));
    }

    private void pr12IncreaseBookCopies() {
        String dateInput = SC.nextLine();
        LocalDate releasedAfter = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd MMM yyyy"));
        Integer bookCopiesIncrease = Integer.parseInt(SC.nextLine());

        System.out.println(this.bookService.increaseAllBookCopiesWithAfterReleaseDate(releasedAfter, bookCopiesIncrease));
    }

    private void pr11ReducedBook() {
        String bookTitle = SC.nextLine();
        System.out.println(this.bookService.getOutputGetReducedInfoDTO(bookTitle));
    }

    private void pr10TotalBookCopies() {
//        System.out.println(this.authorService.getOutputAuthorsFullNameAndTotalCopiesDTO());
        System.out.println(this.authorService.getOutputAuthorsFullNameAndTotalCopiesRecord());
    }

    private void pr09CountBooks() {
        int length = Integer.parseInt(SC.nextLine());
        System.out.println(this.bookService.getOutputCountTitleLongerThan(length));
    }

    private void pr08BookTitleSearch() {
        String input = SC.nextLine();
        System.out.println(this.bookService.getOutputAllAuthorLastNameStartingWith(input));
    }

    private void pr07BookSearch() {
        String input = SC.nextLine();
        System.out.println(this.bookService.getOutputAllBooksTitleContaining(input));
    }

    private void pr06AuthorsSearch() {
        String input = SC.nextLine();
        System.out.println(this.authorService.getOutputAuthorsFirstNameEndingWith(input));
    }

    private void pr05BookReleasedBeforeDate() {
        String dateInput = SC.nextLine();
//        dd-MM-yyyy
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsedDate = LocalDate.parse(dateInput, dateTimeFormatter);

        System.out.println(this.bookService.getOutputAllBooksReleasedBefore(parsedDate));
    }

    private void pr04NotReleasedBooks() {
        int year = Integer.parseInt(SC.nextLine());
        System.out.println(this.bookService.getOutputAllBooksNotReleasedInYear(year));
    }

    private void pr03BookTitlesByPrice() {
        BigDecimal priceLowerThan = new BigDecimal(5);
        BigDecimal priceGreaterThan = new BigDecimal(40);
        System.out.println(this.bookService.getOutputAllBooksByPriceLowerThanAndGreaterThan(priceLowerThan, priceGreaterThan));
    }

    private void pr02BookTitlesGoldenBooks() {
        EditionEnum goldEdition = EditionEnum.GOLD;
        int copiesLessThan = 5_000;
        System.out.println(this.bookService.getOutputAllBooksByEditionAndCopiesLessThan(goldEdition, copiesLessThan));
    }

    private void pr01BookTitlesByAgeRestriction() {
        String input = SC.nextLine();
        System.out.println(this.bookService.getOutputAllBookNamesWithRestriction(input.toUpperCase()));
    }
}
