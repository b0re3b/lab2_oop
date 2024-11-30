package DepositsTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.bankdeposits.validator.XMLValidator;

class XMLValidatorTest {

    @Test
    void testValidateXML_validXML_shouldReturnTrue() {
        // Шлях до валідного XML та XSD
        String xmlPath = "src/test/resources/valid_deposits.xml";
        String xsdPath = "src/test/resources/deposits.xsd";

        // Виклик методу валідації
        boolean isValid = XMLValidator.validateXML(xmlPath, xsdPath);

        // Перевірка, що XML валідний
        assertTrue(isValid);
    }
    @Test
    void testValidateXML_invalidXML_shouldReturnFalse() {
        // Шлях до невалідного XML та XSD
        String xmlPath = "src/test/resources/invalid_deposits.xml"; // XML не відповідає XSD
        String xsdPath = "src/test/resources/deposits.xsd";

        // Виклик методу валідації
        boolean isValid = XMLValidator.validateXML(xmlPath, xsdPath);

        // Перевірка, що XML не валідний
        assertFalse(isValid);
    }
    @Test
    void testValidateXML_emptyXML_shouldReturnFalse() {
        // Шлях до порожнього XML та XSD
        String xmlPath = "src/test/resources/empty_deposits.xml"; // Порожній XML
        String xsdPath = "src/test/resources/deposits.xsd";

        // Виклик методу валідації
        boolean isValid = XMLValidator.validateXML(xmlPath, xsdPath);

        // Перевірка, що порожній XML не валідний
        assertFalse(isValid);
    }
    @Test
    void testValidateXML_verboseMode_shouldLogDetailedErrors() {
        // Шлях до невалідного XML та XSD
        String xmlPath = "src/test/resources/invalid_deposits.xml"; // XML, що не відповідає XSD
        String xsdPath = "src/test/resources/deposits.xsd";

        // Виклик методу валідації з verbose
        boolean isValid = XMLValidator.validateXML(xmlPath, xsdPath, true);

        // Перевірка, що XML не валідний і виведення логів
        assertFalse(isValid);
    }

}
