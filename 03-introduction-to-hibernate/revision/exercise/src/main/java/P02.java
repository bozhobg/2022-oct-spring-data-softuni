import entities.Town;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class P02 {


    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Query queryAllTowns = em.createQuery("FROM Town AS t", Town.class);
        List<Town> allTowns = queryAllTowns.getResultList();

        List<Town> townsMoreThan5Letters = allTowns.stream()
                .filter(t -> t.getName().length() > 5)
                .collect(Collectors.toList());

        // detaching entities from the persistence context
        // em.clear() to detach the whole PC
        for (Town t : townsMoreThan5Letters) {
            em.detach(t);
            t.setName(t.getName().toUpperCase());
        }

        // merges entities to the persistence context (changed) when flushed changes will be persisted in DB
        List<Town> managedTowns = townsMoreThan5Letters.stream()
                .map(em::merge)
                .collect(Collectors.toList());

        em.flush();
        transaction.commit();
        em.close();
    }
}
