package com.bankdeposits.comparator;

import com.bankdeposits.model.DepositModel;
import java.util.Comparator;

/**
 * Utility class for comparing instances of {@link DepositModel} using various criteria.
 */
public class DepositComparator {

    /**
     * Comparator for deposit amount in ascending order.
     */
    public static final Comparator<DepositModel> BY_AMOUNT = Comparator.comparing(DepositModel::getAmount);

    /**
     * Comparator for deposit amount in descending order.
     */
    public static final Comparator<DepositModel> BY_AMOUNT_REVERSE = BY_AMOUNT.reversed();

    /**
     * Comparator for profitability (interest rate) in ascending order.
     */
    public static final Comparator<DepositModel> BY_PROFITABILITY =
            Comparator.comparing(DepositModel::getProfitability);

    /**
     * Comparator for profitability (interest rate) in descending order.
     */
    public static final Comparator<DepositModel> BY_PROFITABILITY_REVERSE =
            BY_PROFITABILITY.reversed();

    /**
     * Comparator for bank name in alphabetical order.
     */
    public static final Comparator<DepositModel> BY_BANK_NAME =
            Comparator.comparing(DepositModel::getName);

    /**
     * Comparator for deposit type.
     */
    public static final Comparator<DepositModel> BY_DEPOSIT_TYPE =
            Comparator.comparing(DepositModel::getType);

    /**
     * Composite comparator that first compares by amount, then by profitability.
     */
    public static final Comparator<DepositModel> BY_AMOUNT_AND_PROFITABILITY =
            BY_AMOUNT.thenComparing(BY_PROFITABILITY);

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private DepositComparator() {}
}
