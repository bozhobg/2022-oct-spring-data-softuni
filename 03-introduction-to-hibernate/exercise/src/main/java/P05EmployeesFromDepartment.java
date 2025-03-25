import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.List;

public class P05EmployeesFromDepartment {
    private static final String SELECT_EMPLOYEES_BY_DEPT_NAME =
                    "FROM Employee e " +
                    "WHERE e.department.name = :deptName " +
                    "ORDER BY e.salary, e.id";
    private static final String DEPARTMENT = "Research and Development";
    private static final String PRINT_FORMAT = "%s %s from %s - $%.2f%n";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

//        em.createQuery("FROM Department d WHERE d.name = :deptName", Department.class)
//                .setParameter("deptName", DEPARTMENT)
//                .getSingleResult()
//                .getEmployees()
//                .stream()
//                .sorted(
//                        Comparator.comparing(Employee::getSalary)
//                        .thenComparing(Employee::getId))
//                .forEach(e ->
//                    System.out.printf(
//                        PRINT_FORMAT
//                        , e.getFirstName()
//                        , e.getLastName()
//                        , e.getDepartment().getName()
//                        , e.getSalary()));

        em.createQuery(SELECT_EMPLOYEES_BY_DEPT_NAME, Employee.class)
                .setParameter("deptName", DEPARTMENT)
                .getResultList()
                .forEach(e -> System.out.printf(
                        PRINT_FORMAT
                        , e.getFirstName()
                        , e.getLastName()
                        , e.getDepartment().getName()
                        , e.getSalary()
                ));

    }
}
