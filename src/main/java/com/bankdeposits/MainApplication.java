package com.bankdeposits;

import com.bankdeposits.comparator.DepositComparator;
import com.bankdeposits.model.DepositModel;
import com.bankdeposits.parser.DOMParser;
import com.bankdeposits.parser.SAXParser;
import com.bankdeposits.parser.StAXParser;
import com.bankdeposits.validator.XMLValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entry point for the Bank Deposits application. This application validates,
 * parses, sorts, and transforms XML data of bank deposits.
 */
public class MainApplication {
    private static final Logger logger = LogManager.getLogger(MainApplication.class);
    private static final String XML_PATH = "src/main/resources/bank_deposits.xml";
    private static final String XSD_PATH = "src/main/resources/bank_deposits.xsd";
    private static final String XSL_PATH = "src/main/resources/transformation.xsl";

    public static void main(String[] args) {
        try {
            // Validate file existence
            if (!filesExist(XML_PATH, XSD_PATH, XSL_PATH)) {
                return;
            }

            // Step 1: Validate XML against XSD
            validateXMLDocument();

            // Step 2: Parse XML using different parsers
            parseXMLDocuments();

            // Step 3: Demonstrate sorting of parsed deposits
            demonstrateSorting();

            // Step 4: Transform XML using XSLT
            transformXMLWithXSLT();

        } catch (Exception e) {
            logger.error("Error occurred in the application", e);
        }
    }

    /**
     * Validates the XML document against the XSD schema.
     */
    private static void validateXMLDocument() {
        boolean isValid = XMLValidator.validateXML(XML_PATH, XSD_PATH, true);
        logger.info("XML Validation Result: " + (isValid ? "Valid" : "Invalid"));
    }

    /**
     * Parses the XML file using SAX, DOM, and StAX parsers.
     */
    private static void parseXMLDocuments() throws Exception {
        // SAX Parser
        SAXParser saxParser = new SAXParser();
        List<DepositModel> saxDeposits = saxParser.parse(XML_PATH);
        logger.info("SAX Parser: Parsed {} deposits", saxDeposits.size());

        // DOM Parser
        DOMParser domParser = new DOMParser();
        List<DepositModel> domDeposits = domParser.parse(XML_PATH);
        logger.info("DOM Parser: Parsed {} deposits", domDeposits.size());

        // StAX Parser
        StAXParser staxParser = new StAXParser();
        List<DepositModel> staxDeposits = staxParser.parse(XML_PATH);
        logger.info("StAX Parser: Parsed {} deposits", staxDeposits.size());
    }

    /**
     * Demonstrates sorting of deposits by amount and profitability.
     */
    private static void demonstrateSorting() throws Exception {
        SAXParser parser = new SAXParser();
        List<DepositModel> deposits = parser.parse(XML_PATH);

        // Sort by deposit amount
        List<DepositModel> sortedByAmount = deposits.stream()
                .sorted(DepositComparator.BY_AMOUNT)
                .collect(Collectors.toList());
        logger.info("Deposits sorted by amount:");
        sortedByAmount.forEach(deposit ->
                logger.info(deposit.getName() + ": " + deposit.getAmount())
        );

        // Sort by profitability in descending order
        List<DepositModel> sortedByProfitability = deposits.stream()
                .sorted(DepositComparator.BY_PROFITABILITY_REVERSE)
                .collect(Collectors.toList());
        logger.info("Deposits sorted by profitability (descending):");
        sortedByProfitability.forEach(deposit ->
                logger.info(deposit.getName() + ": " + deposit.getProfitability() + "%")
        );
    }

    /**
     * Transforms the XML document using an XSLT stylesheet.
     */
    private static void transformXMLWithXSLT() throws TransformerException, IOException {
        // Prepare XML and XSLT sources
        StreamSource xmlSource = new StreamSource(new FileInputStream(XML_PATH));
        StreamSource xslSource = new StreamSource(new FileInputStream(XSL_PATH));

        // Initialize Transformer
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xslSource);

        // Specify output file
        File outputFile = new File("src/main/resources/transformed_output.txt");
        StreamResult result = new StreamResult(new FileOutputStream(outputFile));

        // Perform transformation
        transformer.transform(xmlSource, result);
        logger.info("XML successfully transformed using XSLT to: " + outputFile.getAbsolutePath());
    }

    /**
     * Validates if the required files exist.
     *
     * @param filePaths paths of the files to check.
     * @return true if all files exist, false otherwise.
     */
    private static boolean filesExist(String... filePaths) {
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (!file.exists()) {
                logger.error("File not found: " + filePath);
                return false;
            }
        }
        return true;
    }
}
