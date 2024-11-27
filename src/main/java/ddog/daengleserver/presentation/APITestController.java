package ddog.daengleserver.presentation;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/test")
public class APITestController {
    @GetMapping("")
    public String test() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        return "Hello Daengle World ! This Application is new !" +
                " Made at: " + formattedDate +"   CI/CD SUCCESS";
    }

    @PostMapping("")
    public String testPost(@RequestBody String text) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        return "YOUR REQUEST : " + text +
                "\nMY RESPONSE : Hello Daengle World ! This Application is new ! Made at: " + formattedDate+"   CI/CD SUCCESS";
    }
}
