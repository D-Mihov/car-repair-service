package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.CarType;
import softuni.exam.models.entity.Task;

import java.util.List;

// TODO:
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByCar_CarTypeOrderByPriceDesc(CarType carType);

}
