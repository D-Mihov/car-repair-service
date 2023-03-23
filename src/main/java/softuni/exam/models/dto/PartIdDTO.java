package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PartIdDTO {
    @XmlElement(name = "id")
    @NotNull
    private long id;

    public PartIdDTO() {
    }

    public long getId() {
        return id;
    }
}
