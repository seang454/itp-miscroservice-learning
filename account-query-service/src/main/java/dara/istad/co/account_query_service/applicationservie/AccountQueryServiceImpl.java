package dara.istad.co.account_query_service.applicationservie;

import dara.istad.co.account_query_service.applicationservie.dto.AccountQueryResponse;
import dara.istad.co.account_query_service.applicationservie.mapper.AccountAppDataMapper;
import dara.istad.co.account_query_service.applicationservie.ports.input.service.AccountQueryService;
import dara.istad.co.account_query_service.applicationservie.ports.output.repository.AccountQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountQueryServiceImpl implements AccountQueryService {

    private final AccountQueryRepository accountQueryRepository;
    private final AccountAppDataMapper accountAppDataMapper;

    @Override
    public Mono<AccountQueryResponse> getAccountById(UUID accountId) {
        return accountQueryRepository
                .findById(accountId)
                .map(accountAppDataMapper::accountToAccountQueryResponse);
    }

    @Override
    public Flux<AccountQueryResponse> getAllAccounts() {
        return accountQueryRepository
                .findAll()
                .map(accountAppDataMapper::accountToAccountQueryResponse);
    }
}
