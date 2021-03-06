package application;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by eustali on 11.01.2017.
 */
@SpringBootApplication(exclude={org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class} )
@ComponentScan (basePackages = {"controller","config"})
public class Application extends SpringBootServletInitializer {

    static final Logger logger = Logger.getLogger(Application.class);
    static final String path = "src/main/resources/log4j.properties";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(path);
        SpringApplication.run(Application.class, args);
    }
}
