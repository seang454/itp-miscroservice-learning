package co.istad.dara.common.domain.event;

import co.istad.dara.common.domain.valueobject.AccountStatus;
import co.istad.dara.common.domain.valueobject.AccountTypeCode;
import co.istad.dara.common.domain.valueobject.Money;
import co.istad.dara.common.domain.valueobject.AccountId;
import co.istad.dara.common.domain.valueobject.BranchId;
import co.istad.dara.common.domain.valueobject.CustomerId;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record AccountCreatedEvent (
        AccountId accountId,
        String accountNumber,
        String accountHolder,
        CustomerId customerId,
        AccountTypeCode accountTypeCode,
        AccountStatus accountStatus,
        BranchId branchId,
        Money initialBalance,
        ZonedDateTime createdAt,
        String createdBy
){
}
