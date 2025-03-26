package bg.softuni;

import bg.softuni.entities.Bike;
import bg.softuni.entities.Car;
import bg.softuni.entities.Plane;
import bg.softuni.entities.Truck;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("table_per_class_single");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();


        List<Car> cars = new ArrayList<>();
        List<Truck> trucks = new ArrayList<>();
        List<Bike> bikes = new ArrayList<>();
        List<Plane> planes = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            cars.add(new Car(
                    "carType",
                    "carModel " + i,
                    BigDecimal.valueOf(i),
                    "carFuelType",
                    i + 1
            ));
        }

        for (int i = 1; i < 4; i++) {
            trucks.add(new Truck(
                    "truckType",
                    "truckModel " + i,
                    BigDecimal.valueOf(i),
                    "truckFuelType",
                    10.0 / (double) i
            ));
        }

        for (int i = 1; i < 4; i++) {
            bikes.add(new Bike(
                    "bikeType",
                    "bikeModel " + i,
                    BigDecimal.valueOf(i),
                    "humanPower"
            ));
        }

        for (int i = 1; i < 4; i++) {
            planes.add(new Plane(
                    "planeType",
                    "planeModel " + i,
                    BigDecimal.valueOf(i * 1000),
                    "kerosene",
                    30 * i
            ));
        }

        transaction.begin();
//        TODO: persist couple of entities per type to see resulting tables and relations

        cars.forEach(em::persist);
        trucks.forEach(em::persist);
        bikes.forEach(em::persist);
        planes.forEach(em::persist);

        transaction.commit();

        em.close();
    }
}