package br.com.medvia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public Application() {
        System.out.println("Medvia started OK!");
        
        ServerController sc = new ServerController();
        sc.dbCreate();
    }

}
