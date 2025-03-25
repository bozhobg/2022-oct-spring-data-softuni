import entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class P07AddressesWithEmployeeCount {

    private static final String FORMAT_ADDRESS = "%s, %s - %d employees%n";

    public static void main(String[] args) {
        EntityManagerFactory efm = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = efm.createEntityManager();

        List<Address> rsAddresses = em.createQuery(
                        "SELECT a " +
                                "FROM Address a " +
                                "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

        for (Address address : rsAddresses) {
            System.out.printf(
                    FORMAT_ADDRESS
                    , address.getText()
                    , address.getTown().getName()
                    , address.getEmployees().size());
        }

    }
}
