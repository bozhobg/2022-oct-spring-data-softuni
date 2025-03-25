import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Comparator;

public class P09FindLatest10Projects {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("soft_uni").createEntityManager();

        em.createQuery("FROM Project p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(System.out::println);
    }
}
