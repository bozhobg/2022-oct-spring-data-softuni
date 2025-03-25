import helpers.DepartmentMaxSalary;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class P12EmployeesMaxSalaryWithCtor {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("soft_uni")
                .createEntityManager();


        List<DepartmentMaxSalary> rsDeptMaxSalary = em.createQuery(
                        "SELECT new helpers.DepartmentMaxSalary" +
                                "(E.department.name, MAX(E.salary)) " +
                                "FROM Employee E " +
                                "GROUP BY E.department.id", DepartmentMaxSalary.class)
                .getResultList();

        //TODO: How to order by Employee.id
        rsDeptMaxSalary.forEach(dms -> System.out.printf("%s %.2f%n", dms.getDepartmentName(), dms.getMaxSalary()));
    }
}
