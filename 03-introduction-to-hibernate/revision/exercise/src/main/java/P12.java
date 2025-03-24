import dto.DepartmentSalaryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class P12 {

    private static final BigDecimal MIN_SALARY = BigDecimal.valueOf(30_000);
    private static final BigDecimal MAX_SALARY = BigDecimal.valueOf(70_000);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        String output = em.createQuery("""
                        SELECT new dto.DepartmentSalaryDTO(d.name, max(e.salary))
                        FROM Department AS d
                        JOIN Employee e ON e.department = d
                        WHERE e.salary NOT BETWEEN :minSalary AND :maxSalary
                        GROUP BY d
                        """, DepartmentSalaryDTO.class)
                .setParameter("minSalary", MIN_SALARY)
                .setParameter("maxSalary", MAX_SALARY)
                .getResultStream()
                .map(DepartmentSalaryDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(output);
    }
}
