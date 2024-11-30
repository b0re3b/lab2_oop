package DepositsTest;
import com.bankdeposits.model.DepositModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.bankdeposits.parser.DOMParser;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DOMParserTest {

    private DOMParser domParser;

    @BeforeEach
    void setUp() {
        domParser = new DOMParser();
    }

    @Test
    void testParse_withMissingElements_shouldThrowError() {
        // XML, де відсутні деякі обов'язкові елементи
        String xmlPath = "src/test/resources/missing_elements.xml"; // Створіть файл з відсутніми елементами

        assertThrows(Exception.class, () -> domParser.parse(xmlPath));
    }


    @Test
    void testParse_withInvalidXML_shouldHandleErrors() {
        // Тест для випадку з некоректним XML, де деякі значення будуть некоректні
        String xmlPath = "src/test/resources/invalid_deposits.xml"; // Некоректний XML файл

        assertThrows(Exception.class, () -> domParser.parse(xmlPath));
    }

    @Test
    void testParse_withInvalidDataFormat_shouldHandleError() {
        // XML, де значення є некоректними, наприклад, для поля <amount> текст замість числа
        String xmlPath = "src/test/resources/invalid_data_format.xml"; // Створіть файл з некоректними даними

        assertThrows(Exception.class, () -> domParser.parse(xmlPath));
    }


}