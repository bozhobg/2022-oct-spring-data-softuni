import entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;

public class P10 {

    public static final String OUTPUT_FORMAT = "%s %s ($%.2f)%n";
    public static final BigDecimal SALARY_INCREASE_VALUE = BigDecimal.valueOf(1.12);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        List<String> deptNames = List.of("Engineering", "Tool Design", "Marketing", "Information Services");

        List<Employee> employees = em.createQuery("""
                        FROM Employee AS e
                        WHERE e.department.name IN  :deptNames
                        """, Employee.class)
                .setParameter("deptNames", deptNames)
                .getResultList();

        employees.stream()
                .forEach(e -> e.setSalary(
                        e.getSalary().multiply(SALARY_INCREASE_VALUE)
                ));

        StringBuilder sb = new StringBuilder();

        employees.forEach(e -> sb.append(
                String.format(OUTPUT_FORMAT, e.getFirstName(), e.getLastName(), e.getSalary()))
        );

        transaction.commit();

        System.out.println(sb);

        em.close();
    }


}
