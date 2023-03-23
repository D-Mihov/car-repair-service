package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTaskDTO {
    @XmlElement(name = "date")
    @NotNull
    private String date;
    @XmlElement(name = "price")
    @Positive
    @NotNull
    private BigDecimal price;
    @XmlElement(name = "car")
    @NotNull
    private CarIdDTO car;
    @XmlElement(name = "mechanic")
    @NotNull
    private MechanicNameDTO mechanic;
    @XmlElement(name = "part")
    @NotNull
    private PartIdDTO part;

    public ImportTaskDTO() {
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CarIdDTO getCar() {
        return car;
    }

    public MechanicNameDTO getMechanic() {
        return mechanic;
    }

    public PartIdDTO getPart() {
        return part;
    }
}
