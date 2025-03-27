package bg.softuni.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "store_location")
public class StoreLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "store_location")
    private String storeLocation;

    @OneToMany(mappedBy = "storeLocation")
    private Set<Sale> sales;

    public StoreLocation() {
        this.sales = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public StoreLocation setId(int id) {
        this.id = id;
        return this;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public StoreLocation setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
        return this;
    }

    public Set<Sale> getSales() {
        return sales;
    }
}
