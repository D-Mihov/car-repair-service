package softuni.exam.models.dto;

import softuni.exam.models.entity.CarType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportCarDTO {
    @XmlElement(name = "carMake")
    @Size(min = 2, max = 30)
    @NotNull
    private String carMake;
    @XmlElement(name = "carModel")
    @Size(min = 2, max = 30)
    @NotNull
    private String carModel;
    @XmlElement(name = "year")
    @Positive
    @NotNull
    private int year;
    @XmlElement(name = "plateNumber")
    @Size(min = 2, max = 30)
    @NotNull
    private String plateNumber;
    @XmlElement(name = "kilometers")
    @Positive
    @NotNull
    private int kilometers;
    @XmlElement(name = "engine")
    @DecimalMin(value = "1")
    @NotNull
    private double engine;
    @XmlElement(name = "carType")
    @NotNull
    private CarType carType;

    public ImportCarDTO() {
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getYear() {
        return year;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public int getKilometers() {
        return kilometers;
    }

    public double getEngine() {
        return engine;
    }

    public CarType getCarType() {
        return carType;
    }
}
