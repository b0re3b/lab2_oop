package com.bankdeposits.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

/**
 * Represents a deposit model with details about the deposit,
 * including the bank, depositor, amount, profitability, and duration.
 */
public class DepositModel implements Comparable<DepositModel> {

    private String id; // Unique identifier for the deposit
    private String name; // Name of the bank or deposit
    private String country; // Country where the bank is located
    private String type; // Type of deposit
    private String depositor; // Name of the depositor
    private BigDecimal amount; // Deposit amount
    private BigDecimal profitability; // Profitability (interest rate)
    private Duration timeConstraints; // Duration of the deposit

    /**
     * Default constructor.
     */
    public DepositModel() {}

    /**
     * Full constructor to initialize all fields of the deposit model.
     *
     * @param id              Unique identifier of the deposit.
     * @param name            Name of the bank or deposit.
     * @param country         Country where the bank is located.
     * @param type            Type of deposit.
     * @param depositor       Name of the depositor.
     * @param amount          Amount of the deposit.
     * @param profitability   Interest rate of the deposit.
     * @param timeConstraints Duration of the deposit.
     */
    public DepositModel(String id, String name, String country, String type,
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

    // Getters and setters

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

    /**
     * Compares this deposit model with another based on the deposit amount.
     *
     * @param other Another deposit model to compare to.
     * @return A negative integer, zero, or a positive integer if this deposit
     *         amount is less than, equal to, or greater than the other deposit amount.
     */
    @Override
    public int compareTo(DepositModel other) {
        return this.amount.compareTo(other.amount);
    }

    /**
     * Checks if this deposit model is equal to another object.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositModel deposit = (DepositModel) o;
        return Objects.equals(id, deposit.id) &&
                Objects.equals(name, deposit.name) &&
                Objects.equals(amount, deposit.amount);
    }

    /**
     * Computes the hash code for this deposit model.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount);
    }

    /**
     * Returns a string representation of the deposit model.
     *
     * @return A string containing the deposit details.
     */
    @Override
    public String toString() {
        return "DepositModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", profitability=" + profitability +
                ", timeConstraints=" + timeConstraints +
                '}';
    }
}
