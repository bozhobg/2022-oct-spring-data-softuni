import entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P11 {

    public static final String OUTPUT_LINE_FORMAT = "%s %s - %s - ($%.2f)";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        String input = new Scanner(System.in).nextLine();
        String startsWith = input + "%";

        String output = em.createQuery("""
                        FROM Employee AS e
                        WHERE e.firstName LIKE :startsWith
                        """, Employee.class)
                .setParameter("startsWith", startsWith)
                .getResultStream()
                .map(P11::formatOutputLine)
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(output);
    }

    private static String formatOutputLine(Employee e) {
        return String.format(
                OUTPUT_LINE_FORMAT,
                e.getFirstName(),
                e.getLastName(),
                e.getJobTitle(),
                e.getSalary());
    }
}
