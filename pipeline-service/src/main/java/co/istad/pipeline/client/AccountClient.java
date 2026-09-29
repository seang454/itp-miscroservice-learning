package co.istad.pipeline.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@HttpExchange
public interface AccountClient {

    @GetExchange("/api/v1/account")
    Map<String, Object> getAccount();
}
