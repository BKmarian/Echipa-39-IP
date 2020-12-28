package ProgramareWebJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringBootWebApplication {


    public static void main(String[] args) {

        SpringApplication.run(SpringBootWebApplication.class, args);

    }
}
