package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportMechanicDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MechanicServiceImpl implements MechanicService {
    private final String PATH = "src/main/resources/files/json/mechanics.json";
    private final MechanicRepository mechanicRepository;
    private final MyValidator myValidator;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository, MyValidator myValidator, ModelMapper modelMapper, Gson gson) {
        this.mechanicRepository = mechanicRepository;
        this.myValidator = myValidator;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        String json = this.readMechanicsFromFile();
        ImportMechanicDTO[] mechanicDTOS = this.gson.fromJson(json, ImportMechanicDTO[].class);

        return Arrays.stream(mechanicDTOS)
                .map(this::importMechanic)
                .collect(Collectors.joining("\n"));
    }

    private String importMechanic(ImportMechanicDTO importMechanicDTO) {
        boolean isValid = this.myValidator.isValid(importMechanicDTO);

        if (isValid) {
            Optional<Mechanic> optMechanic = this.mechanicRepository.findByEmail(importMechanicDTO.getEmail());

            if (optMechanic.isEmpty()) {
                Mechanic mechanic = this.modelMapper.map(importMechanicDTO, Mechanic.class);

                this.mechanicRepository.save(mechanic);

                return String.format("Successfully imported mechanic %s %s", mechanic.getFirstName(), mechanic.getLastName());
            } else {
                return "Invalid mechanic";
            }
        } else {
            return "Invalid mechanic";
        }
    }
}
