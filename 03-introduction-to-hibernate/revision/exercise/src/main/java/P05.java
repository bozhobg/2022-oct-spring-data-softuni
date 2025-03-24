import entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class P05 {

    private static final String DEPT_NAME = "Research and Development";
    private static final String OUTPUT_FORMAT = "%s %s from %s - %.2f%n";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        List<Employee> employees = em.createQuery("""
                        SELECT e 
                        FROM Employee AS e
                        WHERE e.department.name = :deptName
                        ORDER BY e.salary ASC, e.id ASC
                        """, Employee.class)
                .setParameter("deptName", DEPT_NAME)
                .getResultList();

        employees.stream().forEach(e -> System.out.printf(
                OUTPUT_FORMAT,
                e.getFirstName(),
                e.getLastName(),
                e.getDepartment().getName(),
                e.getSalary())
        );

        em.close();
    }
}
