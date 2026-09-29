package co.istad.dara.common.domain.valueobject;

import java.util.UUID;

public class CustomerId{
    private final UUID value;

    public UUID getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public CustomerId(UUID value){
        this.value = value;
    }
}
