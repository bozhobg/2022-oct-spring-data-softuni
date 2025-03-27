package bg.softuni;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hospital");
        EntityManager em = emf.createEntityManager();

//        TODO: console service layer

        em.close();
    }
}