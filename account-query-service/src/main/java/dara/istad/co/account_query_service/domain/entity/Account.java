package dara.istad.co.account_query_service.domain.entity;

import co.istad.dara.common.domain.valueobject.AccountStatus;
import co.istad.dara.common.domain.valueobject.AccountTypeCode;
import co.istad.dara.common.domain.valueobject.Currency;
import co.istad.dara.common.domain.valueobject.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private UUID accountId;
    private UUID customerId;
    private UUID branchId;

    private String accountNumber;
    private String accountHolder;

    private Money money;

    private AccountTypeCode accountTypeCode;
    private AccountStatus accountStatus;

    private ZonedDateTime createdAt;
    private String createdBy;
    private ZonedDateTime updatedAt;
    private String updatedBy;
}
