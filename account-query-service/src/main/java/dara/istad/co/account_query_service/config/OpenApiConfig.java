package dara.istad.co.account_query_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:20263}")
    private String serverPort;

    @Bean
    public OpenAPI accountQueryOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Account Query Microservice API")
                        .description("Reactive CQRS Query Service for Bank Account Information (Spring WebFlux + R2DBC)")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ITP Support")
                                .email("support@istad.co"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server().url("http://localhost:" + serverPort).description("Local Development Server")
                ));
    }

    @Bean
    public RouterFunction<ServerResponse> swaggerRouter() {
        URI swaggerUri = URI.create("/webjars/swagger-ui/index.html?url=/v3/api-docs");
        return RouterFunctions.route(
                RequestPredicates.GET("/swagger-ui.html")
                        .or(RequestPredicates.GET("/swagger-ui"))
                        .or(RequestPredicates.GET("/swagger-ui/")),
                req -> ServerResponse.temporaryRedirect(swaggerUri).build()
        );
    }
}
