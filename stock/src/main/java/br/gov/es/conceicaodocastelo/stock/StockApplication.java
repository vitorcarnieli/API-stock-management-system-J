package br.gov.es.conceicaodocastelo.stock;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StockApplication {

	public static void main(String[] args) {
        System.out.println(new Date());
		SpringApplication.run(StockApplication.class, args);
	}

	@GetMapping(path = "/")
	public String helloWord() {
        return "Hello Word!";
	}

}
