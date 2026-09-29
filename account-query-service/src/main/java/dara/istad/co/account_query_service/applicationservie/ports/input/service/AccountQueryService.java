package dara.istad.co.account_query_service.applicationservie.ports.input.service;

import dara.istad.co.account_query_service.applicationservie.dto.AccountQueryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

// Input port for rest or input data to domain
public interface AccountQueryService {
    Mono<AccountQueryResponse> getAccountById(UUID accountId);

    Flux<AccountQueryResponse> getAllAccounts();
}
