package dara.istad.co.account_query_service.dataaccess.mapper;

import dara.istad.co.account_query_service.dataaccess.entity.AccountEntity;
import dara.istad.co.account_query_service.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountDataAccessMapper {

    @Mapping(source = "money.amount", target = "balance")
    @Mapping(source = "money.currency", target = "currency")
    @Mapping(target = "newEntity", ignore = true)
    AccountEntity accountToAccountEntity(Account account);

    @Mapping(source = "balance", target = "money.amount")
    @Mapping(source = "currency", target = "money.currency")
    Account accountEntityToAccount(AccountEntity accountEntity);
}
