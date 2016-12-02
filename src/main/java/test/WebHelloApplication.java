package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableAsync
public class WebHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebHelloApplication.class, args);
	}
}
