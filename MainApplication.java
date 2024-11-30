package com.bankdeposits;

import com.bankdeposits.comparator.DepositComparator;
import com.bankdeposits.model.DepositModel;
import com.bankdeposits.parser.DOMParser;
import com.bankdeposits.parser.SAXParser;
import com.bankdeposits.parser.StAXParser;
import com.bankdeposits.validator.XMLValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class MainApplication {
    private static final Logger logger = LogManager.getLogger(MainApplication.class);
    private static final String XML_PATH = "src/main/resources/bank_deposits.xml";
    private static final String XSD_PATH = "src/main/resources/bank_deposits.xsd";

    public static void main(String[] args) {
        try {
            // Валідація XML
            validateXMLDocument();

            // Парсинг різними методами
            parseXMLDocuments();

            // Демонстрація сортування
            demonstrateSorting();

        } catch (Exception e) {
            logger.error("Помилка в роботі додатку", e);
        }
    }

    private static void validateXMLDocument() {
        boolean isValid = XMLValidator.validateXML(XML_PATH, XSD_PATH, true);
        logger.info("XML Validation Result: " + (isValid ? "Valid" : "Invalid"));
    }

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

    private static void demonstrateSorting() throws Exception {
        SAXParser parser = new SAXParser();
        List<DepositModel> deposits = parser.parse(XML_PATH);

        // Сортування за сумою вкладу
        List<DepositModel> sortedByAmount = deposits.stream()
                .sorted(DepositComparator.BY_AMOUNT)
                .collect(Collectors.toList());

        logger.info("Deposits sorted by amount:");
        sortedByAmount.forEach(deposit ->
                logger.info(deposit.getName() + ": " + deposit.getAmount())
        );

        // Сортування за відсотковою ставкою
        List<DepositModel> sortedByProfitability = deposits.stream()
                .sorted(DepositComparator.BY_PROFITABILITY_REVERSE)
                .collect(Collectors.toList());

        logger.info("Deposits sorted by profitability (descending):");
        sortedByProfitability.forEach(deposit ->
                logger.info(deposit.getName() + ": " + deposit.getProfitability() + "%")
        );
    }
}