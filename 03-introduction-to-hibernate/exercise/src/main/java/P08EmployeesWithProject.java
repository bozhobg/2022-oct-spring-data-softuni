import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P08EmployeesWithProject {

    private static final String FORMAT_OUTPUT = "%s %s - %s%n%s%n";

    public static void main(String[] args) {
        EntityManager em =
                Persistence
                        .createEntityManagerFactory("soft_uni")
                        .createEntityManager();

        final int empId = new Scanner(System.in).nextInt();

        Employee employee =
                em.createQuery("SELECT e FROM Employee e WHERE e.id = :empId", Employee.class)
                        .setParameter("empId", empId)
                        .getSingleResult();

        String projects = employee.getProjects()
                .stream()
                .map(Project::getName)
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.printf(
                FORMAT_OUTPUT
                , employee.getFirstName()
                , employee.getLastName()
                , employee.getJobTitle()
                , projects);

    }
}
