package com.bankdeposits.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLValidator {
    private static final Logger LOGGER = Logger.getLogger(XMLValidator.class.getName());

    /**
     * Перевірка XML-документа на відповідність XSD-схемі
     *
     * @param xmlPath шлях до XML-файлу
     * @param xsdPath шлях до XSD-файлу
     * @return true, якщо XML валідний, інакше false
     */
    public static boolean validateXML(String xmlPath, String xsdPath) {
        try {
            // Створення фабрики схем
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Завантаження XSD-схеми
            Source schemaSource = new StreamSource(new File(xsdPath));
            Schema schema = factory.newSchema(schemaSource);

            // Створення валідатора
            Validator validator = schema.newValidator();

            // Джерело для XML-документа
            Source xmlSource = new StreamSource(new File(xmlPath));

            // Перевірка XML
            validator.validate(xmlSource);

            LOGGER.info("XML документ " + xmlPath + " є валідним.");
            return true;

        } catch (SAXException e) {
            LOGGER.log(Level.SEVERE, "XML документ не відповідає XSD схемі: " + e.getMessage(), e);
            return false;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Помилка читання файлів: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Перевантажений метод валідації з більш детальним виведенням помилок
     *
     * @param xmlPath шлях до XML-файлу
     * @param xsdPath шлях до XSD-файлу
     * @param verbose режим детального виведення
     * @return true, якщо XML валідний, інакше false
     */
    public static boolean validateXML(String xmlPath, String xsdPath, boolean verbose) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaSource = new StreamSource(new File(xsdPath));
            Schema schema = factory.newSchema(schemaSource);
            Validator validator = schema.newValidator();
            Source xmlSource = new StreamSource(new File(xmlPath));

            validator.validate(xmlSource);

            if (verbose) {
                LOGGER.info("XML документ повністю відповідає XSD схемі.");
                LOGGER.info("Шлях до XML: " + xmlPath);
                LOGGER.info("Шлях до XSD: " + xsdPath);
            }

            return true;

        } catch (SAXException e) {
            if (verbose) {
                LOGGER.log(Level.SEVERE, "Детальна інформація про помилку валідації:", e);
                LOGGER.severe("Рядок: " + e.getLocalizedMessage());
            }
            return false;
        } catch (IOException e) {
            if (verbose) {
                LOGGER.log(Level.SEVERE, "Помилка читання файлів:", e);
            }
            return false;
        }
    }

    // Приватний конструктор для утилітного класу
    private XMLValidator() {}
}