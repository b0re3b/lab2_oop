package com.bankdeposits.model;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.util.Objects;

public class DepositModel implements Comparable<Deposit> {
    private String id;
    private String name;
    private String country;
    private String type;
    private String depositor;
    private BigDecimal amount;
    private BigDecimal profitability;
    private Duration timeConstraints;

    // Конструктор за замовчуванням
    public Deposit() {}

    // Повний конструктор
    public Deposit(String id, String name, String country, String type,
                   String depositor, BigDecimal amount,
                   BigDecimal profitability, Duration timeConstraints) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.type = type;
        this.depositor = depositor;
        this.amount = amount;
        this.profitability = profitability;
        this.timeConstraints = timeConstraints;
    }

    // Геттери та сеттери
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepositor() {
        return depositor;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getProfitability() {
        return profitability;
    }

    public void setProfitability(BigDecimal profitability) {
        this.profitability = profitability;
    }

    public Duration getTimeConstraints() {
        return timeConstraints;
    }

    public void setTimeConstraints(Duration timeConstraints) {
        this.timeConstraints = timeConstraints;
    }

    // Реалізація Comparable для базового сортування за сумою вкладу
    @Override
    public int compareTo(Deposit other) {
        return this.amount.compareTo(other.amount);
    }

    // Перевизначення equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deposit deposit = (Deposit) o;
        return Objects.equals(id, deposit.id) &&
                Objects.equals(name, deposit.name) &&
                Objects.equals(amount, deposit.amount);
    }

    // Перевизначення hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount);
    }

    // Зручний toString для логування та виведення
    @Override
    public String toString() {
        return "Deposit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", profitability=" + profitability +
                '}';
    }
}