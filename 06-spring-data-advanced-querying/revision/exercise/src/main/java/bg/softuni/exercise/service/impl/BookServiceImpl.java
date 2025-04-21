package bg.softuni.exercise.service.impl;

import bg.softuni.exercise.entities.Book;
import bg.softuni.exercise.repositories.BookRepository;
import bg.softuni.exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
}
