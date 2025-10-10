package io.bitryon;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "io.bitryon.example" })
public class BitryonIntegrationExampleBootApplication {

	public static void main(String[] args) {
		//io.bitryon.logger.boostrap.LoggingInitiation.premain(null); // must load before everything. or add in META-INF/spring.factories 
		new SpringApplicationBuilder(BitryonIntegrationExampleBootApplication.class).run(args);
	}
}
