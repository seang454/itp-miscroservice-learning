package co.istad.dara.common.domain.valueobject;


import co.istad.dara.common.domain.exception.DomainException;

import java.math.BigDecimal;

public record Money(
        BigDecimal amount,
        Currency currency
) {

    public Money deposit(Money other) {
        checkCurrency(other.currency);
        return new Money(this.amount.add(other.amount), other.currency);
    }

    public Money withdraw(Money other) {
        checkCurrency(other.currency);
        if (this.amount.compareTo(other.amount) < 0) {
            throw new DomainException("Not enough funds");
        }
        return new Money(this.amount.subtract(other.amount), other.currency);
    }

    public boolean isLessThanOrEqual(Money initMoney){
        checkCurrency(initMoney.currency);
        return this.amount.compareTo(initMoney.amount) <= 0;
    }

    public void checkCurrency(Currency currency){
        if (this.currency != currency){
            throw new DomainException("Currency is invalid");
        }
    }
}
