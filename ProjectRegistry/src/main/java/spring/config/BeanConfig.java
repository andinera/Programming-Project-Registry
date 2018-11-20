package spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScans(value= {@ComponentScan("spring.uow")})
public class BeanConfig {

}
