import entities.Department;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class P12EmployeesMaxSalary {
    public static void main(String[] args) {
        EntityManager em =
                Persistence.createEntityManagerFactory("soft_uni").createEntityManager();

        List<Department> depts = em.createQuery("FROM Department d", Department.class)
                .getResultList();

        for (Department dept : depts) {
            String deptName = dept.getName();
            OptionalDouble maxSalary = dept.getEmployees().stream()
                    .map(Employee::getSalary)
                    .mapToDouble(BigDecimal::doubleValue)
                    .filter(v -> v < 30000 || v > 70000)
                    .max();

            if (maxSalary.isPresent()) {
                System.out.printf("%s %.2f%n", deptName, maxSalary.getAsDouble());
            }
        }
    }
}
