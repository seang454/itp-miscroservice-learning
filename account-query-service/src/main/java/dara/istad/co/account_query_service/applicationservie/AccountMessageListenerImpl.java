package dara.istad.co.account_query_service.applicationservie;

import co.istad.dara.common.domain.event.AccountCreatedEvent;
import dara.istad.co.account_query_service.applicationservie.mapper.AccountAppDataMapper;
import dara.istad.co.account_query_service.applicationservie.ports.input.message.listener.AccountMessageListener;
import dara.istad.co.account_query_service.applicationservie.ports.output.repository.AccountQueryRepository;
import dara.istad.co.account_query_service.domain.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountMessageListenerImpl implements AccountMessageListener {

    private final AccountQueryRepository accountQueryRepository;
    private final AccountAppDataMapper accountAppDataMapper;

    @Override
    public void onAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent) {
        Account account = accountAppDataMapper.accountCreatedEventToAccount(accountCreatedEvent);

        accountQueryRepository.save(account)
                .doOnSuccess(data -> log.info("Saved account = {} successfully", accountCreatedEvent.accountId()))
                .doOnError(error -> log.error("Failed to save account = {}, error: {}", accountCreatedEvent.accountId(), error.getMessage()))
                .subscribe();
    }
}
