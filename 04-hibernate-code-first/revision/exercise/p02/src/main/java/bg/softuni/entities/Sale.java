package bg.softuni.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(
            targetEntity = Product.class,
            fetch = FetchType.EAGER,
            cascade = {},
            optional = false
    )
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(
            targetEntity = Customer.class,
            fetch = FetchType.EAGER,
            cascade = {},
            optional = false
    )
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(
            targetEntity = StoreLocation.class,
            fetch = FetchType.EAGER,
            cascade = {},
            optional = false
    )
    @JoinColumn(name = "store_location_id")
    private StoreLocation storeLocation;

    @Basic
    private Date date;

    public Sale() {
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Sale setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Sale setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public StoreLocation getStoreLocation() {
        return storeLocation;
    }

    public Sale setStoreLocation(StoreLocation storeLocation) {
        this.storeLocation = storeLocation;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Sale setDate(Date date) {
        this.date = date;
        return this;
    }
}
