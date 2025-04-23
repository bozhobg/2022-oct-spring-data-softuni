package bg.softuni.exercise.repositories;

import bg.softuni.exercise.domain.dto.AuthorFullNameCopiesSumDTO;
import bg.softuni.exercise.domain.dto.AuthorFullNameCopiesSumRecord;
import bg.softuni.exercise.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findAuthorsByBooks_ReleaseDateBefore(LocalDate date);
    List<Author> findAllByFirstNameEndsWith(String endingWith);
    List<Author> findAllByLastNameStartingWithIgnoreCase(String startingWith);

//    @Procedure(procedureName = "bookshop_system.sp_books_count_by_author_names")
//    int callGetBooksCountByAuthorNames(@Param("first_name") String firstName, @Param("last_name") String lastName);

    @Procedure(procedureName = "sp_books_count_by_author_names")
    int callGetBooksCountByAuthorNames(String firstName, String lastName);

    @Query("""
            SELECT
                new bg.softuni.exercise.domain.dto.AuthorFullNameCopiesSumDTO(
                    a.firstName,
                    a.lastName,
                    SUM(b.copies)
                )
            FROM Author AS a
            JOIN a.books AS b
            GROUP BY a
            """)
    List<AuthorFullNameCopiesSumDTO> selectAuthorsFullNameAndSumAllCopiesDTO();

    @Query("""
            SELECT
                new bg.softuni.exercise.domain.dto.AuthorFullNameCopiesSumRecord(
                    a.firstName,
                    a.lastName,
                    SUM(b.copies)
                )
            FROM Author AS a
            JOIN a.books AS b
            GROUP BY a
            """)
    List<AuthorFullNameCopiesSumRecord> selectAuthorsFullNameAndSumAllCopiesRecord();
}
