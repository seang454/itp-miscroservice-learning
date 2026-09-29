package dara.istad.co.account_query_service.applicationservie.ports.input.message.listener;
import co.istad.dara.common.domain.event.AccountCreatedEvent;

public interface AccountMessageListener {

    void  onAccountCreatedEvent(AccountCreatedEvent accountCreatedEvent);
}
