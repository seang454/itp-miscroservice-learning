package dara.istad.co.account_query_service.dataaccess.entity;

import co.istad.dara.common.domain.valueobject.AccountTypeCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "account_types")
public class AccountTypeEntity {

    @Id
    private UUID accountTypeId;

    private AccountTypeCode accountTypeCode;

}
