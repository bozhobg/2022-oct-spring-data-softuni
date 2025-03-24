import entities.Employee;
import entities.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class P08 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = emf.createEntityManager();

        int empId = Integer.parseInt(new Scanner(System.in).nextLine());

        Employee emp = em.createQuery("""
                        FROM Employee AS e
                        WHERE e.id = :empId
                        """, Employee.class)
                .setParameter("empId", empId)
                .getSingleResult();

        StringBuilder sb = new StringBuilder();
        String empData = String.format(
                "%s %s - %s",
                emp.getFirstName(), emp.getLastName(), emp.getJobTitle());
        sb.append(empData);

        emp.getProjects().stream()
                .map(Project::getName)
                .sorted(String::compareTo)
                .forEach(n -> sb.append(System.lineSeparator()).append("\t").append(n));

        System.out.println(sb);
    }
}
