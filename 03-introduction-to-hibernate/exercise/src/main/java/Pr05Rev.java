import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Pr05Rev {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();


        em.createQuery("from Employee as e " +
                                "where e.department.name = 'Research and Development'" +
                                "order by e.salary, e.id"
                        , Employee.class)
                .getResultList()
                .forEach(e ->
                        System.out.printf("%s %s from %s - $%.2f\n  "
                                , e.getFirstName(), e.getLastName(), e.getDepartment(), e.getSalary()
                        ));
    }
}
