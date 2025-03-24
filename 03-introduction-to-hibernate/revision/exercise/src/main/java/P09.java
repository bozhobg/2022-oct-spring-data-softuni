import entities.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;

public class P09 {

                                            //    2005-09-01 00:00:00.0
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.n";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private static final String OUTPUT_FORMAT = """
            Project name: %s
            \tProject Description: %s
            \tProject Start Date: %s
            \tProject End Date: %s
            """;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        String output = em.createQuery("""
                        FROM Project AS p
                        ORDER BY p.startDate DESC
                        """, Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .map(P09::formatProjectDataToString)
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(output);
    }

    private static String formatProjectDataToString(Project pr) {
        LocalDateTime endDate = pr.getEndDate();

        return String.format(
                OUTPUT_FORMAT,
                pr.getName(),
                pr.getDescription(),
                DATE_TIME_FORMATTER.format(pr.getStartDate()),
                endDate == null ? null : DATE_TIME_FORMATTER.format(endDate));
    }
}
