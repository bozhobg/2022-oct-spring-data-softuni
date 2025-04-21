package bg.softuni.exercise.service;

import bg.softuni.exercise.entities.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {

    void seedBooks(List<Book> books);

    void printAllBooksAfter(LocalDate date);

    boolean isDataSeeded();

    void findAllByAuthorNamesAndSortByReleaseDateAndTitle(String firstName, String lastName);
}
