package bg.softuni;

import bg.softuni.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.Year;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bill_payment_system");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tr = em.getTransaction();

        tr.begin();

        User user = new User().setFirstName("firstName1")
                .setLastName("lastName1")
                .setPassword("password1")
                .setEmail("email1");

        CreditCard creditCard = new CreditCard();

        // case: parent (abstract) setters return reference? (polymorphism) to the parent not the child (child instantiated empty)
        // child.setAbstractParent() if builder setter doesn't return child instance(reference)
        creditCard.setOwner(user);
        creditCard.setNumber("number1");
        creditCard.setCardType(CardType.TYPE_1)
                .setExpirationMonth(MonthEnum.JANUARY)
                .setExpirationYear(Year.of(2029));

        // Polymorphism of abstract class
        BillingDetail billingDetailCreditCard = creditCard;

        em.persist(creditCard);

        tr.commit();

        em.close();
    }
}