package bg.softuni.exercise.service;

import bg.softuni.exercise.domain.entities.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorService {

    Author getRandomAuthor();

    void seedAuthors(List<Author> authors);

    boolean isDataSeeded();

    void printAuthorsWithBooksBefore(LocalDate date);

    void printAllAuthorsOrderedByBooksCount();

    String getOutputAuthorsFirstNameEndingWith(String firstNameEnding);

    String getOutputAuthorsFullNameAndTotalCopiesDTO();

    String getOutputAuthorsFullNameAndTotalCopiesRecord();

    int callBooksCountAuthorFirstAndLastName(String firstName, String lastName);
}