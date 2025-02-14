package lk.ijse.dep13.example2;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Example2Application {

    public static void main(String[] args) {
        SpringApplication.run(Example2Application.class, args);
    }

}

@RestController
class MyHttpController {

    // http://localhost:9090/search?q=ijse#test
    @GetMapping("/**")
    public String urlVsUri(HttpServletRequest request) {
        return """
        <h1>Hello URL vs URI</h1>
        <h2>URL: %s</h2>
        <h2>URI: %s</h2>
        """.formatted(request.getRequestURL(),
                request.getRequestURI());
    }
}
