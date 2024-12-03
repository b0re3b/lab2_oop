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

/**
 * Utility class for validating XML files against XSD schemas.
 */
public class XMLValidator {
    private static final Logger LOGGER = Logger.getLogger(XMLValidator.class.getName());

    /**
     * Validates an XML document against an XSD schema.
     *
     * @param xmlPath the path to the XML file.
     * @param xsdPath the path to the XSD schema file.
     * @return true if the XML is valid, false otherwise.
     */
    public static boolean validateXML(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Load XSD schema
            Source schemaSource = new StreamSource(new File(xsdPath));
            Schema schema = factory.newSchema(schemaSource);

            // Create Validator instance
            Validator validator = schema.newValidator();

            // Load XML source
            Source xmlSource = new StreamSource(new File(xmlPath));

            // Validate XML
            validator.validate(xmlSource);

            LOGGER.info("XML document " + xmlPath + " is valid.");
            return true;

        } catch (SAXException e) {
            LOGGER.log(Level.SEVERE, "XML document is not valid against the XSD schema: " + e.getMessage(), e);
            return false;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading files: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Validates an XML document against an XSD schema with verbose error reporting.
     *
     * @param xmlPath the path to the XML file.
     * @param xsdPath the path to the XSD schema file.
     * @param verbose whether to log detailed error messages.
     * @return true if the XML is valid, false otherwise.
     */
    public static boolean validateXML(String xmlPath, String xsdPath, boolean verbose) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaSource = new StreamSource(new File(xsdPath));
            Schema schema = factory.newSchema(schemaSource);
            Validator validator = schema.newValidator();
            Source xmlSource = new StreamSource(new File(xmlPath));

            // Perform validation
            validator.validate(xmlSource);

            if (verbose) {
                LOGGER.info("XML document is fully compliant with the XSD schema.");
                LOGGER.info("XML Path: " + xmlPath);
                LOGGER.info("XSD Path: " + xsdPath);
            }

            return true;

        } catch (SAXException e) {
            if (verbose) {
                LOGGER.log(Level.SEVERE, "Detailed validation error information:", e);
                LOGGER.severe("Error message: " + e.getLocalizedMessage());
            }
            return false;
        } catch (IOException e) {
            if (verbose) {
                LOGGER.log(Level.SEVERE, "File reading error:", e);
            }
            return false;
        }
    }

    // Private constructor to prevent instantiation
    private XMLValidator() {
    }
}
