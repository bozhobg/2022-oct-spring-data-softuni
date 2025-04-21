package bg.softuni.exercise.service;

import bg.softuni.exercise.entities.Book;
import bg.softuni.exercise.entities.enums.EditionEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {

    void seedBooks(List<Book> books);

    void printAllBooksAfter(LocalDate date);

    boolean isDataSeeded();

    void findAllByAuthorNamesAndSortByReleaseDateAndTitle(String firstName, String lastName);

    String getOutputAllBookNamesWithRestriction(String ageRestrictionValue);

    String getOutputAllBooksByEditionAndCopiesLessThan(EditionEnum goldEdition, Integer copiesLessThan);

    String getOutputAllBooksByPriceLowerThanAndGreaterThan(BigDecimal priceLowerThan, BigDecimal priceGreaterThan);

    String getOutputAllBooksNotReleasedInYear(int year);

    String getOutputAllBooksReleasedBefore(LocalDate date);

    String getOutputAllTitleContaining(String containing);

    String getOutputAllAuthorLastNameStartingWith(String input);
}
