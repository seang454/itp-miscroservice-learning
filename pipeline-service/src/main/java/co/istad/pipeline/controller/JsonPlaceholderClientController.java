package co.istad.pipeline.controller;

import co.istad.pipeline.client.JsonPlaceholderClient;
import co.istad.pipeline.client.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JsonPlaceholderClientController {

    private final JsonPlaceholderClient jsonPlaceholderClient;

    @GetMapping("/users")
    List<UserResponse> getUsers() {
       return  jsonPlaceholderClient.getUsers();
    }
}
