package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportPartDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;
import softuni.exam.util.MyValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService {
    private final String PATH = "src/main/resources/files/json/parts.json";
    private final PartRepository partRepository;
    private final MyValidator myValidator;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, MyValidator myValidator, ModelMapper modelMapper, Gson gson) {
        this.partRepository = partRepository;
        this.myValidator = myValidator;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importParts() throws IOException {
        String json = this.readPartsFileContent();
        ImportPartDTO[] partDTOS = this.gson.fromJson(json, ImportPartDTO[].class);

        return Arrays.stream(partDTOS)
                .map(this::importPart)
                .collect(Collectors.joining("\n"));
    }

    private String importPart(ImportPartDTO importPartDTO) {
        boolean isValid = this.myValidator.isValid(importPartDTO);

        if (isValid) {
            Optional<Part> optPart = this.partRepository.findByPartName(importPartDTO.getPartName());

            if (optPart.isEmpty()) {
                Part part = this.modelMapper.map(importPartDTO, Part.class);

                this.partRepository.save(part);

                return String.format("Successfully imported part %s - %.2f", part.getPartName(), part.getPrice());
            } else {
                return "Invalid part";
            }
        } else {
            return "Invalid part";
        }
    }
}
