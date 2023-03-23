package softuni.exam.config;

import softuni.exam.util.MyValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// TODO:
@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public MyValidator getValidator() {
        return new MyValidator();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter((Converter<String, LocalDateTime>) mappingContext -> {
            LocalDateTime parse = LocalDateTime.parse(mappingContext.getSource(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return parse;
        });
        return modelMapper;
    }

    @Bean
    public Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

}
