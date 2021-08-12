package pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pizza.service.LoadDataFromExcel;

import java.io.IOException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException {
        SpringApplication appl = new SpringApplication(Main.class);
        appl.run(args);
    }
}