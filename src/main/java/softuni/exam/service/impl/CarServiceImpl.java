package softuni.exam.service.impl;

import softuni.exam.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCarDTO;
import softuni.exam.models.dto.ImportCarRootDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.service.CarService;
import softuni.exam.util.MyValidator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final String PATH = "src/main/resources/files/xml/cars.xml";
    private final CarRepository carRepository;
    private final MyValidator myValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, MyValidator myValidator, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.myValidator = myValidator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ImportCarRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportCarRootDTO rootDTO = (ImportCarRootDTO) unmarshaller.unmarshal(new File(PATH));

        return rootDTO
                .getCarDTOS()
                .stream()
                .map(this::importCar)
                .collect(Collectors.joining("\n"));
    }

    private String importCar(ImportCarDTO importCarDTO) {
        boolean isValid = this.myValidator.isValid(importCarDTO);

        if (isValid) {
            Optional<Car> optCar =
                    this.carRepository.findByPlateNumber(importCarDTO.getPlateNumber());
            if (optCar.isEmpty()) {
                Car car = this.modelMapper.map(importCarDTO, Car.class);

                this.carRepository.save(car);

                return String.format("Successfully imported car %s - %s",
                        car.getCarMake(), car.getCarModel());
            } else {
                return "Invalid car";
            }
        } else {
            return "Invalid car";
        }
    }
}
