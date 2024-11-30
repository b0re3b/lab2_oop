package com.bankdeposits.comparator;

import java.util.Comparator;

public class DepositComparator {
    // Компаратор за сумою вкладу (за зростанням)
    public static final Comparator<DepositComparator> BY_AMOUNT = Comparator.comparing(DepositComparator::getAmount);

    // Компаратор за сумою вкладу (за спаданням)
    public static final Comparator<DepositComparator> BY_AMOUNT_REVERSE = BY_AMOUNT.reversed();

    // Компаратор за відсотковою ставкою (за зростанням)
    public static final Comparator<DepositComparator> BY_PROFITABILITY =
            Comparator.comparing(DepositComparator::getProfitability);

    // Компаратор за відсотковою ставкою (за спаданням)
    public static final Comparator<DepositComparator> BY_PROFITABILITY_REVERSE =
            BY_PROFITABILITY.reversed();

    // Компаратор за назвою банку (за алфавітом)
    public static final Comparator<DepositComparator> BY_BANK_NAME =
            Comparator.comparing(DepositComparator::getName);

    // Компаратор за типом вкладу
    public static final Comparator<DepositComparator> BY_DEPOSIT_TYPE =
            Comparator.comparing(DepositComparator::getType);

    // Складений компаратор: спочатку за сумою, потім за відсотковою ставкою
    public static final Comparator<DepositComparator> BY_AMOUNT_AND_PROFITABILITY =
            BY_AMOUNT.thenComparing(BY_PROFITABILITY);

    // Приватний конструктор для утилітного класу
    private DepositComparators() {}
}