package ddog.daengleserver.presentation;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class APITestController {

    @GetMapping("")
    public String test() {
        return "Hello Daengle World ! This Application is new !" + "Made at+"+System.currentTimeMillis();
    }

    @PostMapping("")
    public String testPost(@RequestBody String text) {
        return "YOUR REQUEST : " + text +"\nMY RESPONSE : Hello Daengle World ! This Application is new !";
    }
}
