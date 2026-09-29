package dara.istad.co.account_query_service.dataaccess.repository;


import co.istad.dara.common.domain.valueobject.AccountTypeCode;
import dara.istad.co.account_query_service.dataaccess.entity.AccountTypeEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountTypeRepository extends R2dbcRepository<AccountTypeEntity, UUID> {
    Optional<AccountTypeEntity> findByAccountTypeCode(AccountTypeCode accountTypeCode);

    boolean existsByAccountTypeCode(AccountTypeCode accountTypeCode);
}
