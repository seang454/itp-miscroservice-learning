package co.istad.pipeline.controller;

import co.istad.pipeline.client.AccountClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Map;

@RestController
@RequestMapping("/client/account")
//@RequiredArgsConstructor
@Slf4j
public class AccountClientController {

    private final AccountClient accountClient;
    private final CircuitBreaker circuitBreaker;

    public AccountClientController(AccountClient accountClient, CircuitBreakerRegistry registry) {
        this.accountClient = accountClient;
        circuitBreaker = registry.circuitBreaker("accountCB");
    }

    @GetMapping("/secured")
//    @CircuitBreaker(name = "accountCB", fallbackMethod = "fallbackAccounts")
    public Map<String, Object> secureAccount() {
//        return accountClient.getAccount();
        log.debug("Debug secureAccount");
//        throw new RuntimeException("Error internal");
        try{
            return circuitBreaker.executeSupplier(accountClient::getAccount);
        }catch (CallNotPermittedException e){
            return Map.of("error", e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Map.of("error", e.getMessage());
        }
    }


//    public Map<String, Object> fallbackAccounts(Throwable ex) {
//        return Map.of("message", "Fallback accounts",
//                "error",ex.getMessage());
//    }

}
