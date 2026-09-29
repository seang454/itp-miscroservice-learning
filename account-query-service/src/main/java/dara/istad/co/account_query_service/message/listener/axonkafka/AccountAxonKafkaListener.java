package dara.istad.co.account_query_service.message.listener.axonkafka;

import co.istad.dara.common.domain.event.AccountCreatedEvent;
import dara.istad.co.account_query_service.applicationservie.ports.input.message.listener.AccountMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@ProcessingGroup("account-group")
public class AccountAxonKafkaListener {

    private final AccountMessageListener accountMessageListener;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("on accountCreatedEvent: {}", accountCreatedEvent);
        accountMessageListener.onAccountCreatedEvent(accountCreatedEvent);
    }
}
