package dara.istad.co.account_query_service.applicationservie.dto;

import co.istad.dara.common.domain.valueobject.AccountStatus;
import co.istad.dara.common.domain.valueobject.AccountTypeCode;
import co.istad.dara.common.domain.valueobject.Money;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.UUID;

@Schema(description = "Account Query Response")
public record AccountQueryResponse(
        @Schema(description = "Unique Identifier of the Account", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
        UUID accountId,

        @Schema(description = "Account Number", example = "001-234-5678")
        String accountNo,

        @Schema(description = "Account Holder Full Name", example = "John Doe")
        String accountHolder,

        @Schema(description = "Current Balance and Currency details")
        Money money,

        @Schema(description = "Account Type Code", example = "SAVINGS")
        AccountTypeCode accountTypeCode,

        @Schema(description = "Current Account Status", example = "ACTIVE")
        AccountStatus accountStatus,

        @Schema(description = "Customer ID", example = "b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22")
        UUID customerId,

        @Schema(description = "Branch ID", example = "c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33")
        UUID branchId,

        @Schema(description = "Account Creation Timestamp")
        ZonedDateTime createdAt
) {
}
