package co.istad.pipeline.config;

import co.istad.pipeline.client.AccountClient;
import co.istad.pipeline.client.JsonPlaceholderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpInterfaceConfig {

//    @Bean
//    public HttpInterfaceFactory httpInterfaceFactory() {
//        return new HttpInterfaceFactory();
//    }

    @Bean
    public JsonPlaceholderClient userService(HttpInterfaceFactory factory) {
        return factory.createNormalClient("https://jsonplaceholder.typicode.com",
                JsonPlaceholderClient.class);
    }

    @Bean
    public AccountClient accountService(HttpInterfaceFactory factory) {
        return factory.createLoadBalanceClient("http://account",
                AccountClient.class);
    }

}
