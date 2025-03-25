import helpers.DepartmentMaxSalary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class Pr12Rev {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        List<DepartmentMaxSalary> dmsList = em.createQuery(
                        "select new helpers.DepartmentMaxSalary(e.department.name, max(e.salary)) " +
                                "from Employee e " +
                                "group by e.department "
                , DepartmentMaxSalary.class
                )
                .getResultList();

        dmsList.stream()
                .filter((dms) ->
                        (dms.getMaxSalary().compareTo(BigDecimal.valueOf(30_000)) < 0
                                || dms.getMaxSalary().compareTo(BigDecimal.valueOf(70_000)) > 0))
                .forEach(dms -> System.out.printf("%s %.2f\n", dms.getDepartmentName(), dms.getMaxSalary().doubleValue()));
    }
}
