package bg.softuni.exercise.repositories;

import bg.softuni.exercise.domain.dto.BookReducedDTO;
import bg.softuni.exercise.domain.entities.Book;
import bg.softuni.exercise.domain.entities.enums.AgeRestrictionEnum;
import bg.softuni.exercise.domain.entities.enums.EditionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    int deleteAllByCopiesLessThan(Integer copies);

    @Query("""
            SELECT b
            FROM Book AS b
            WHERE YEAR(b.releaseDate) <> :year
            """)
    List<Book> selectAllByReleaseYearDifferentThan(int year);

    @Query("""
            SELECT COUNT(b)
            FROM Book AS b
            WHERE LENGTH(b.title) > :length
            """)
    int selectAllByTitleLengthGreaterThan(int length);

    @Query("""
            SELECT new bg.softuni.exercise.domain.dto.BookReducedDTO
            (b.title, b.editionType, b.ageRestriction,b.price)
            FROM Book AS b
            WHERE b.title = :bookTitle
            """)
    Optional<BookReducedDTO> selectBookReducedDTOByTitle(String bookTitle);

    @Modifying
    @Transactional
    @Query("""
            UPDATE Book AS b
            SET b.copies = b.copies +  :copiesIncrease
            WHERE b.releaseDate > :afterDate
            """)
    int increaseBookCopiesWithReleaseDateAfter(LocalDate afterDate, Integer copiesIncrease);
}
