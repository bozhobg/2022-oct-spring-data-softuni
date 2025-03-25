import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P04EmployeesWithSalaryMoreThan50000 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

//        List<String> namesEmployees =
        em.createQuery("SELECT e.firstName FROM Employee e WHERE e.salary > 50000", String.class)
                .getResultList()
                .forEach(System.out::println);
    }
}
