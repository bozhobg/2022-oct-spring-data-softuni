package bg.softuni.exercise.service;

import bg.softuni.exercise.entities.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorService {

    Author getRandomAuthor();

    void seedAuthors(List<Author> authors);

    boolean isDataSeeded();

    void printAuthorsWithBooksBefore(LocalDate date);

    void printAllAuthorsOrderedByBooksCount();
}