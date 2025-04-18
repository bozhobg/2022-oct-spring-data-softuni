package bg.softuni.exercise.service.impl;

import bg.softuni.exercise.entities.Author;
import bg.softuni.exercise.repositories.AuthorRepository;
import bg.softuni.exercise.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        long count = this.authorRepository.count();
        int randomId = new Random().nextInt((int) count) + 1;

        return this.authorRepository.findById(randomId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found for random id: " + randomId + "!"));
    }

    @Override
    public void seedAuthors(List<Author> authors) {
        this.authorRepository.saveAll(authors);
    }

    @Override
    public boolean isDataSeeded() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public void printAuthorsWithBooksBefore(LocalDate date) {

        this.authorRepository.findAuthorsByBooks_ReleaseDateBefore(date)
                .stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .forEach(System.out::println);
    }

    @Override
    public void printAllAuthorsOrderedByBooksCount() {

        this.authorRepository.findAll()
                .stream()
                .sorted((a1, a2) -> a2.getBooks().size() - a1.getBooks().size())
                .map(a -> a.getFirstName() + " " + a.getLastName() + " " + a.getBooks().size())
                .forEach(System.out::println);
    }
}
