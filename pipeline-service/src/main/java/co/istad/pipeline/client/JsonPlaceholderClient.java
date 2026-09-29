package co.istad.pipeline.client;

import co.istad.pipeline.client.dto.UserResponse;
import org.apache.catalina.User;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface JsonPlaceholderClient {

    @GetExchange("/users")
    List<UserResponse> getUsers();

}
