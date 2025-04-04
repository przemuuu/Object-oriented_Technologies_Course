package pl.edu.agh.to.school;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {

    // Zwykły napis
//    @GetMapping
//    public String greeting() {
//        return "Technologie obiektowe";
//    }

    // JSON
    @GetMapping
    public List<String> greeting() {
        return List.of("Technologie", "obiektowe");
    }
}
