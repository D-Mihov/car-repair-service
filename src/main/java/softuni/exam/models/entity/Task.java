package softuni.exam.models.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(optional = false)
    private Part part;

    @ManyToOne(optional = false)
    private Mechanic mechanic;

    @ManyToOne(optional = false)
    private Car car;

    public Task() {
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("Car %s %s with %dkm\n" +
                "-Mechanic: %s %s - task №%d\n" +
                "--Engine: %.1f\n" +
                "---Price: %.2f$",
                this.car.getCarMake(), this.car.getCarModel(), this.car.getKilometers(),
                this.mechanic.getFirstName(), this.mechanic.getLastName(), this.id,
                this.car.getEngine(),
                this.price);
    }
}
