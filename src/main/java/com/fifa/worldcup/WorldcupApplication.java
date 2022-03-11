package com.fifa.worldcup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fifa.worldcup")
public class WorldcupApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldcupApplication.class, args);
	}

}
