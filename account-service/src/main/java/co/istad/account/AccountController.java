package co.istad.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    @GetMapping
    public Map<String, Object> unsecureCustomer() {
        return Map.of("data", "secure Account");
    }
}
