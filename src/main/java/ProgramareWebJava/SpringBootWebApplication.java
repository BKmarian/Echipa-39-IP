package ProgramareWebJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class SpringBootWebApplication {

//    @Resource
//    static StorageService storageService;

    public static void main(String[] args) {

        SpringApplication.run(SpringBootWebApplication.class, args);
//        storageService.deleteAll();
//        storageService.init();

    }

}
