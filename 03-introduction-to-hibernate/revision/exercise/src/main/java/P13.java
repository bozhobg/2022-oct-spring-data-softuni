import entities.Town;
import jakarta.persistence.*;

import java.util.Scanner;

public class P13 {

    private static final String OUTPUT_FORMAT_SINGLE_ADDRESS = "%d address in %s deleted";
    private static final String OUTPUT_FORMAT = "%d address in %s deleted";
    private static final String TOWN_NOT_FOUND_FORMAT = "Town: %s not found";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        String input = new Scanner(System.in).nextLine();

        Town town;
        try {
            town = em.createQuery("""
                                    FROM Town AS t
                                    WHERE t.name = :input
                                    """,
                            Town.class)
                    .setParameter("input", input)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException nre) {

            em.close();
            throw new NoResultException(String.format(TOWN_NOT_FOUND_FORMAT, input));
        }

        EntityTransaction tr = em.getTransaction();
//        TODO: unfinished
        tr.begin();

        em.remove(town);
        em.flush();

        tr.commit();

        em.close();
    }
}
