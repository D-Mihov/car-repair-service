package softuni.exam.service.impl;

import softuni.exam.models.entity.*;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportTaskDTO;
import softuni.exam.models.dto.ImportTaskRootDTO;
import softuni.exam.models.entity.*;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.TaskService;
import softuni.exam.util.MyValidator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final String PATH = "src/main/resources/files/xml/tasks.xml";
    private final TaskRepository taskRepository;
    private final MyValidator myValidator;
    private final ModelMapper modelMapper;
    private final MechanicRepository mechanicRepository;
    private final CarRepository carRepository;
    private final PartRepository partRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, MyValidator myValidator, MechanicRepository mechanicRepository, CarRepository carRepository, PartRepository partRepository) {
        this.taskRepository = taskRepository;
        this.myValidator = myValidator;
        this.modelMapper = new ModelMapper();
        this.modelMapper.addConverter(ctx -> LocalDateTime.parse(ctx.getSource(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), String.class, LocalDateTime.class);
        this.mechanicRepository = mechanicRepository;
        this.carRepository = carRepository;
        this.partRepository = partRepository;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ImportTaskRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ImportTaskRootDTO rootDTO = (ImportTaskRootDTO) unmarshaller.unmarshal(new File(PATH));

        return rootDTO
                .getTaskDTOS()
                .stream()
                .map(this::importTask)
                .collect(Collectors.joining("\n"));
    }

    private String importTask(ImportTaskDTO importTaskDTO) {
        boolean isValid = this.myValidator.isValid(importTaskDTO);

        if (isValid) {
            Optional<Mechanic> optMechanic = this.mechanicRepository.findByFirstName(importTaskDTO.getMechanic().getFirstName());
            if (optMechanic.isPresent()) {
                Task task = this.modelMapper.map(importTaskDTO, Task.class);
                Car car = this.carRepository.findById(importTaskDTO.getCar().getId()).orElse(null);
                Part part = this.partRepository.findById(importTaskDTO.getPart().getId()).orElse(null);

                if (car == null || part == null) {
                    return "Invalid task";
                }

                task.setCar(car);
                task.setPart(part);
                task.setMechanic(optMechanic.get());
                this.taskRepository.save(task);

                return String.format("Successfully imported task %.2f", task.getPrice());
            } else {
                return "Invalid task";
            }
        } else {
            return "Invalid task";
        }
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        List<Task> tasks = this.taskRepository.findAllByCar_CarTypeOrderByPriceDesc(CarType.coupe);

        return tasks
                .stream()
                .map(Task::toString)
                .collect(Collectors.joining("\n"));
    }
}
