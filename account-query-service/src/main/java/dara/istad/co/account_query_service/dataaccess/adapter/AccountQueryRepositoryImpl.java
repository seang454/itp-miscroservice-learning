package dara.istad.co.account_query_service.dataaccess.adapter;

import dara.istad.co.account_query_service.applicationservie.ports.output.repository.AccountQueryRepository;
import dara.istad.co.account_query_service.dataaccess.entity.AccountEntity;
import dara.istad.co.account_query_service.dataaccess.mapper.AccountDataAccessMapper;
import dara.istad.co.account_query_service.dataaccess.repository.AccountReactiveRepository;
import dara.istad.co.account_query_service.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountQueryRepositoryImpl implements AccountQueryRepository {

    private final AccountReactiveRepository accountReactiveRepository;
    private final AccountDataAccessMapper accountDataAccessMapper;

    @Override
    public Mono<Account> save(Account account) {

        AccountEntity accountEntity = accountDataAccessMapper
                .accountToAccountEntity(account);

        // Spring Data R2DBC's save() checks the @Id field to decide between INSERT or UPDATE.
        // Since accountId is pre-set (comes from Kafka event as a real UUID, not null),
        // R2DBC assumes the record already exists and issues an UPDATE — which matches nothing
        // and silently saves 0 rows. By calling setNewEntity(true), we tell R2DBC (via the
        // Persistable interface) that this is a brand-new entity and it should always do an INSERT.
        accountEntity.setNewEntity(true);

        log.info("Retrieve data from AccountDomain to AccountEntity: {}", accountEntity.getAccountId());

        return accountReactiveRepository.save(accountEntity)
                .map(accountDataAccessMapper::accountEntityToAccount);
    }

    @Override
    public Mono<Account> findById(UUID accountId) {
        return accountReactiveRepository.findById(accountId)
                .map(accountDataAccessMapper::accountEntityToAccount);
    }

    @Override
    public Flux<Account> findAll() {
        return accountReactiveRepository.findAll()
                .map(accountDataAccessMapper::accountEntityToAccount);
    }
}

