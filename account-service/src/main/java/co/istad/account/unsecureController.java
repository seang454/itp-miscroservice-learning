package co.istad.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class unsecureController {
//    @Value("${service.name}") String name;
//    @Value("${secret.weak-password}") String weakPassword;
//    @Value("${secret.strong-password}") String strongPassword;


    @GetMapping("/test")
    public Map<String, Object> test() {
        return Map.of(
                "name", "name",
                "wp", "weakPassword",
                "sp", "strongPassword"
        );
    }
}
