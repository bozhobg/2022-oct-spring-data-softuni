import entities.Town;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class P02ChangeCasing {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Query queryAllTowns = em.createQuery("FROM Town AS t", Town.class);
        List<Town> listAllTowns = queryAllTowns.getResultList();

        List<Town> listTownsNameMoreThan5Chars = new ArrayList<>();

        for (Town town : listAllTowns) {
            if (town.getName().length() > 5) {
                listTownsNameMoreThan5Chars.add(town);
            }
        }

        listTownsNameMoreThan5Chars.forEach(em::detach);
        listTownsNameMoreThan5Chars.forEach(t -> t.setName(t.getName().toUpperCase()));
        listTownsNameMoreThan5Chars.forEach(em::merge);
        em.flush();

        transaction.commit();
    }
}
