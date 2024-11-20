package ddog.daengleserver.presentation;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    @GetMapping("/login")
    public String login() {
        return "SUCCESS";
    }

    @GetMapping("/admin")
    public String admin() {
        return "ADMIN";
    }

    @GetMapping("/get-info")
    public String getInfo() {
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        System.out.println(credentials);
        return "SUCCESS";
    }

}
