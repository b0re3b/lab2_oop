package DepositsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import com.bankdeposits.parser.StAXParser;
import com.bankdeposits.model.DepositModel;

import static org.junit.jupiter.api.Assertions.*;

class StAXParserTest {

    private StAXParser stAXParser;

    @BeforeEach
    void setUp() {
        stAXParser = new StAXParser();
    }

    @Test
    void testParse() throws Exception {
        // XML файл з депозитами
        String xmlPath = "src/test/resources/bank_deposits.xml"; // Вказуємо шлях до файлу

        // Виконання парсингу
        List<DepositModel> deposits = stAXParser.parse(xmlPath);

        // Перевірка правильності результату
        assertNotNull(deposits);
        assertEquals(2, deposits.size()); // Припустимо, що в XML 2 депозити

        // Перевірка першого депозиту
        DepositModel firstDeposit = deposits.get(0);
        assertEquals("1", firstDeposit.getId());
        assertEquals("ПриватБанк", firstDeposit.getName());
        assertEquals("Україна", firstDeposit.getCountry());
        assertEquals("Терміновий", firstDeposit.getType());
        assertEquals("Іван Іванов", firstDeposit.getDepositor());
        assertEquals(new BigDecimal("10000.50"), firstDeposit.getAmount());
        assertEquals(new BigDecimal("12.5"), firstDeposit.getProfitability());
        assertEquals(Duration.ofDays(365), firstDeposit.getTimeConstraints()); // Тривалість 1 рік

        // Перевірка другого депозиту
        DepositModel secondDeposit = deposits.get(1);
        assertEquals("2", secondDeposit.getId());
        assertEquals("Ощадбанк", secondDeposit.getName());
        assertEquals("Україна", secondDeposit.getCountry());
        assertEquals("Нерухомість", secondDeposit.getType());
        assertEquals("Петро Петров", secondDeposit.getDepositor());
        assertEquals(new BigDecimal("25000.75"), secondDeposit.getAmount());
        assertEquals(new BigDecimal("10.2"), secondDeposit.getProfitability());
        assertEquals(Duration.ofDays(730), secondDeposit.getTimeConstraints()); // Тривалість 2 роки
    }
    @Test
    void testParse_withInvalidData_shouldHandleErrors() throws Exception {
        // XML з некоректними даними, наприклад, текст замість чисел для Amount або Profitability
        String xmlPath = "src/test/resources/invalid_data_format.xml"; // Створіть файл з некоректними даними

        List<DepositModel> deposits = stAXParser.parse(xmlPath);

        // Перевірка, чи парсер не викидає виключення і замінює некоректні дані на 0
        assertNotNull(deposits);
        DepositModel deposit = deposits.get(0);

        // Amount та Profitability мають бути 0, оскільки були некоректні значення
        assertEquals(BigDecimal.ZERO, deposit.getAmount());
        assertEquals(BigDecimal.ZERO, deposit.getProfitability());
    }
    @Test
    void testParse_withEmptyXML_shouldReturnEmptyList() throws Exception {
        // Порожній XML файл
        String xmlPath = "src/test/resources/empty_deposits.xml"; // Створіть порожній XML файл

        List<DepositModel> deposits = stAXParser.parse(xmlPath);

        // Перевірка, що результат є порожнім списком
        assertNotNull(deposits);
        assertTrue(deposits.isEmpty());
    }

}
