import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Scanner;

public class P11FindEmployeesByFirstName {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("soft_uni").createEntityManager();

        final String beginsWith = new Scanner(System.in).nextLine();

        em.createQuery("FROM Employee e " +
                "WHERE e.firstName LIKE :beginsWith", Employee.class)
                .setParameter("beginsWith", beginsWith + "%")
                .getResultList()
                .forEach(e -> System.out.println(e.formatterNamesJobTitleSalary()));
    }
}
