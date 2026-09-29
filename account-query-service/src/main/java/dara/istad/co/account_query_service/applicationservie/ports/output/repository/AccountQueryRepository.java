package dara.istad.co.account_query_service.applicationservie.ports.output.repository;

import dara.istad.co.account_query_service.domain.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

// Output port for data access technologies like, out data from domain
public interface AccountQueryRepository {

    // Save account
    Mono<Account> save(Account account);

    Mono<Account> findById(UUID accountId);

    Flux<Account> findAll();
}
