package bg.softuni.exercise.repositories;

import bg.softuni.exercise.entities.Book;
import bg.softuni.exercise.entities.enums.AgeRestrictionEnum;
import bg.softuni.exercise.entities.enums.EditionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);
    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(AgeRestrictionEnum ageRestrictionEnum);
    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionEnum editionEnum, Integer copies);
    List<Book> findAllByPriceIsLessThanOrPriceIsGreaterThan(BigDecimal lowerThan, BigDecimal greaterThan);
    List<Book> findAllByReleaseDateIsBefore(LocalDate date);
    List<Book> findAllByTitleContainingIgnoreCase(String containing);
    List<Book> findAllByAuthor_LastNameStartingWithIgnoreCase(String authorLastNameStartingWith);

    @Query("""
            SELECT b
            FROM Book AS b
            WHERE YEAR(b.releaseDate) <> :year
            """)
    List<Book> findAllByReleaseYearDifferentThan(int year);
}
