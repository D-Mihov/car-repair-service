package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MechanicNameDTO {
    @XmlElement(name = "firstName")
    @NotNull
    private String firstName;

    public MechanicNameDTO() {
    }

    public String getFirstName() {
        return firstName;
    }
}
