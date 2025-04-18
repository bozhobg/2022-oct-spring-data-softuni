package bg.softuni.exercisemore.repository;

import bg.softuni.exercisemore.entities.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findUsersByEmailEndingWith(String domain);

    @Transactional
    List<User> getAllByLastTimeLoggedInBefore(LocalDateTime beforeDate);

}
