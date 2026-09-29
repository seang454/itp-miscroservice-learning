package dara.istad.co.account_query_service.dataaccess.repository;

import dara.istad.co.account_query_service.dataaccess.entity.AccountEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountReactiveRepository extends R2dbcRepository<AccountEntity, UUID> {
}
