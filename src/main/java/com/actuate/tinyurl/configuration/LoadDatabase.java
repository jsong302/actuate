package com.actuate.tinyurl.configuration;

import com.actuate.tinyurl.model.URL;
import com.actuate.tinyurl.repository.URLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(URLRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new URL("https://www.google.com")));
        };
    }
}