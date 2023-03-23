package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Part;

import java.util.Optional;

// TODO:
public interface PartRepository extends JpaRepository<Part, Long> {


    Optional<Part> findByPartName(String partName);
}
