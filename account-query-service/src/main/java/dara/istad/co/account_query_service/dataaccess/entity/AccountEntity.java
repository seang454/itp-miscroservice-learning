package dara.istad.co.account_query_service.dataaccess.entity;

import co.istad.dara.common.domain.valueobject.AccountStatus;
import co.istad.dara.common.domain.valueobject.AccountTypeCode;
import co.istad.dara.common.domain.valueobject.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
// Implements Persistable<UUID> so that Spring Data R2DBC can correctly
// determine whether to issue an INSERT or UPDATE when save() is called.
// Without this, R2DBC checks if @Id is null to decide — but since accountId
// is always pre-set from the Kafka event (never null), it would always do an
// UPDATE, match nothing, and silently save 0 rows to the database.
@Table(name = "accounts")
public class AccountEntity implements Persistable<UUID> {

    @Id
    private UUID accountId;

    // @Transient tells Spring Data R2DBC NOT to map this field to a database column.
    // It is only used in-memory to signal whether the entity is new (INSERT) or existing (UPDATE).
    @Transient
    private boolean newEntity;

    // Required by Persistable — R2DBC uses getId() to retrieve the entity's primary key.
    @Override
    public UUID getId() {
        return accountId;
    }

    // Required by Persistable — R2DBC calls isNew() before save() to decide:
    //   true  → do INSERT (new record)
    //   false → do UPDATE (existing record)
    // @JsonIgnore prevents this method from being accidentally serialized as a JSON field.
    @Override
    @JsonIgnore
    public boolean isNew() {
        return newEntity;
    }

    private UUID customerId;
    private UUID branchId;

    private String accountNumber;
    private String accountHolder;

    private BigDecimal balance;
    private Currency currency;

    private AccountTypeCode accountTypeCode;
    private AccountStatus accountStatus;

    private ZonedDateTime createdAt;
    private String createdBy;
    private ZonedDateTime updatedAt;
    private String updatedBy;

    // We can use this as well for save as version not use persistable
//    @Version
//    private Long version;
}
