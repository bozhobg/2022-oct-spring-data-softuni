import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Pr10Rev {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        em.createQuery(
                "select e from Employee e " +
                        "where e.department.name " +
                        "in ('Engineering', 'Tool Design', 'Marketing', 'Information Service')"
                , Employee.class)
                .getResultList()
                .forEach((e) -> {
                    e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
                    System.out.printf("%s %s ($%.2f)\n"
                            , e.getFirstName(), e.getLastName(), e.getSalary());
                });

        transaction.commit();


    }
}
