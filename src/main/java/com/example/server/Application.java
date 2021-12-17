package com.example.server;

import com.example.server.enumerations.Status;
import com.example.server.models.Server;
import com.example.server.repository.ServerRepository;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository) {
        return args -> {
            serverRepository.save(new Server(null, "10.0.0.1",
                    "Ubuntu Linux", "16 GB", "Personal PC",
                    "http://localhost:8080/server" +
                    "/image/server1.png", Status.SERVER_DOWN));

            serverRepository.save(new Server(null, "192.168.0.2",
                    "Ubuntu " + "Linux", "16 GB", "Proxy Server"
                    , "http://localhost:8080/server" + "/image/server2.png",
                    Status.SERVER_DOWN));

        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }

}
