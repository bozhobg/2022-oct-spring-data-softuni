import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class P10IncreaseSalary {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("soft_uni")
                .createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.createQuery("FROM Employee e " +
                                "WHERE e.department.name IN" +
                                "('Engineering', 'Tool Design', 'Marketing', 'Information Services')"
                        , Employee.class)
                .getResultList()
                .forEach(e -> {
                    e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
                    System.out.println(e.formatterNamesSalary());
                });


        transaction.commit();
    }
}
