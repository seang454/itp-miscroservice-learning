package dara.istad.co.account_query_service.rest;

import dara.istad.co.account_query_service.applicationservie.dto.AccountQueryResponse;
import dara.istad.co.account_query_service.applicationservie.ports.input.service.AccountQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Queries", description = "Reactive APIs for querying account information")
public class AccountQueryController {

    private final AccountQueryService accountQueryService;

    @Operation(
            summary = "Get account by ID",
            description = "Retrieves account details for the specified account UUID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account found and returned successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AccountQueryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content
            )
    })
    @GetMapping("/{accountId}")
    public Mono<AccountQueryResponse> getAccountById(
            @Parameter(description = "Account Unique Identifier (UUID)", required = true, example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
            @PathVariable UUID accountId
    ) {
        return accountQueryService.getAccountById(accountId);
    }

    @Operation(
            summary = "Get all accounts",
            description = "Retrieves all accounts as a reactive stream from the read model database."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Accounts retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = AccountQueryResponse.class)))
            )
    })
    @GetMapping
    public Flux<AccountQueryResponse> getAllAccounts() {
        return accountQueryService.getAllAccounts();
    }
}
