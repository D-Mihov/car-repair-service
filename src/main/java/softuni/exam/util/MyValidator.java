package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class MyValidator {
    private final Validator localValidator;

    public MyValidator() {
        this.localValidator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    public boolean isValid(Object object) {
        Set<ConstraintViolation<Object>> errors =
                this.localValidator.validate(object);

        return errors.isEmpty();
    }
}
