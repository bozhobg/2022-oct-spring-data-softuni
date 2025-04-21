package bg.softuni.exercise.service.impl;

import bg.softuni.exercise.constants.TemplateFormatsAndMessages;
import bg.softuni.exercise.entities.Book;
import bg.softuni.exercise.entities.enums.AgeRestrictionEnum;
import bg.softuni.exercise.entities.enums.EditionEnum;
import bg.softuni.exercise.exception.InvalidEnumValue;
import bg.softuni.exercise.repositories.BookRepository;
import bg.softuni.exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void seedBooks(List<Book> books) {
        this.bookRepository.saveAll(books);
    }

    @Override
    public void printAllBooksAfter(LocalDate date) {
        this.bookRepository.findAllByReleaseDateAfter(date)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    @Override
    public boolean isDataSeeded() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public void findAllByAuthorNamesAndSortByReleaseDateAndTitle(String firstName, String lastName) {

        this.bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(firstName, lastName)
                .stream()
                .map(b -> b.getTitle() + " " + b.getReleaseDate() + " " + b.getCopies())
                .forEach(System.out::println);
    }

    @Override
    public String getOutputAllBookNamesWithRestriction(String ageRestrictionName) {
        AgeRestrictionEnum ageRestrictionEnum;

        try {
            ageRestrictionEnum = AgeRestrictionEnum.valueOf(ageRestrictionName);
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValue(String.format(TemplateFormatsAndMessages.INVALID_AGE_RESTRICTION, ageRestrictionName));
        }

        return getParsedOutput(
                this.bookRepository.findAllByAgeRestriction(ageRestrictionEnum),
                Book::getTitle
        );
    }

    @Override
    public String getOutputAllBooksByEditionAndCopiesLessThan(EditionEnum goldEdition, Integer copiesLessThan) {
        return getParsedOutput(
                this.bookRepository.findAllByEditionTypeAndCopiesLessThan(goldEdition, copiesLessThan),
                Book::getTitle
        );
    }

    @Override
    public String getOutputAllBooksByPriceLowerThanAndGreaterThan(BigDecimal priceLowerThan, BigDecimal priceGreaterThan) {
        List<Book> books = this.bookRepository.findAllByPriceIsLessThanOrPriceIsGreaterThan(priceLowerThan, priceGreaterThan);

        return getParsedOutput(
                books,
                b -> String.format(TemplateFormatsAndMessages.FORMAT_BOOK_TITLE_PRICE, b.getTitle(), b.getPrice())
        );
    }

    @Override
    public String getOutputAllBooksNotReleasedInYear(int year) {
        return getParsedOutput(this.bookRepository.findAllByReleaseYearDifferentThan(year), Book::getTitle);
    }

    @Override
    public String getOutputAllBooksReleasedBefore(LocalDate date) {
        return getParsedOutput(
                this.bookRepository.findAllByReleaseDateIsBefore(date),
                b -> String.format(
                        TemplateFormatsAndMessages.FORMAT_BOOK_TITLE_EDITION_PRICE,
                        b.getTitle(), b.getEditionType().name(), b.getPrice())
        );
    }

    @Override
    public String getOutputAllTitleContaining(String containing) {
        return getParsedOutput(
                this.bookRepository.findAllByTitleContainingIgnoreCase(containing),
                b -> String.format(
                        TemplateFormatsAndMessages.FORMAT_BOOK_TITLE_AUTHOR_NAME,
                        b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName())
        );
    }

    @Override
    public String getOutputAllAuthorLastNameStartingWith(String input) {
        return getParsedOutput(
                this.bookRepository.findAllByAuthor_LastNameStartingWithIgnoreCase(input),
                b -> String.format(
                        TemplateFormatsAndMessages.FORMAT_BOOK_TITLE_AUTHOR_NAME,
                        b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName())
        );
    }

    private String getParsedOutput(Collection<Book> books, Function<Book, String> stringParser) {
        return books.stream()
                .map(stringParser)
                .collect(Collectors.joining(System.lineSeparator()));
    }


}
