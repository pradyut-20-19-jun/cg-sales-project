package com.cg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class CrudProjectApplication {
	static final Logger log = LoggerFactory.getLogger(CrudProjectApplication.class);

	public static void main(String[] args) {
		log.info("Before Starting application");
		SpringApplication.run(CrudProjectApplication.class, args);

		log.debug("Starting CrudProjectApplication in debug with {} args", args.length);
		log.info("Starting CrudProjectApplication with {} args.", args.length);
	}
}
