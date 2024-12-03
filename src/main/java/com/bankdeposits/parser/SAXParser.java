package com.bankdeposits.parser;

import com.bankdeposits.model.DepositModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * A parser for parsing XML files into a list of {@link DepositModel} objects using SAX.
 */
public class SAXParser {

    /**
     * Parses an XML file and converts it into a list of {@link DepositModel} objects.
     *
     * @param xmlPath the path to the XML file to be parsed.
     * @return a list of parsed {@link DepositModel} objects.
     * @throws Exception if an error occurs during parsing.
     */
    public List<DepositModel> parse(String xmlPath) throws Exception {
        List<DepositModel> deposits = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {
            private DepositModel currentDeposit;
            private StringBuilder elementValue = new StringBuilder();

            /**
             * Triggered when the parser encounters the start of an element.
             *
             * @param uri        the Namespace URI.
             * @param localName  the local name (without prefix).
             * @param qName      the qualified name (with prefix).
             * @param attributes the attributes attached to the element.
             */
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if ("Deposit".equals(qName)) {
                    currentDeposit = new DepositModel();
                    currentDeposit.setId(attributes.getValue("id")); // Parse "id" attribute
                }
                elementValue.setLength(0); // Clear the current element value
            }

            /**
             * Triggered when the parser encounters the text content of an element.
             *
             * @param ch     the characters from the XML document.
             * @param start  the start position in the character array.
             * @param length the number of characters to read from the array.
             */
            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                elementValue.append(ch, start, length);
            }

            /**
             * Triggered when the parser encounters the end of an element.
             *
             * @param uri       the Namespace URI.
             * @param localName the local name (without prefix).
             * @param qName     the qualified name (with prefix).
             */
            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                String value = elementValue.toString().trim();

                switch (qName) {
                    case "Name":
                        if (!value.isEmpty()) currentDeposit.setName(value);
                        break;
                    case "Country":
                        if (!value.isEmpty()) currentDeposit.setCountry(value);
                        break;
                    case "Type":
                        if (!value.isEmpty()) currentDeposit.setType(value);
                        break;
                    case "Depositor":
                        if (!value.isEmpty()) currentDeposit.setDepositor(value);
                        break;
                    case "Amount":
                        try {
                            currentDeposit.setAmount(new BigDecimal(value));
                        } catch (NumberFormatException e) {
                            currentDeposit.setAmount(BigDecimal.ZERO); // Default value or error handling
                        }
                        break;
                    case "Profitability":
                        try {
                            currentDeposit.setProfitability(new BigDecimal(value));
                        } catch (NumberFormatException e) {
                            currentDeposit.setProfitability(BigDecimal.ZERO); // Default value or error handling
                        }
                        break;
                    case "TimeConstraints":
                        try {
                            long timeInDays = Long.parseLong(value); // Convert string to days
                            currentDeposit.setTimeConstraints(Duration.ofDays(timeInDays));
                        } catch (Exception e) {
                            currentDeposit.setTimeConstraints(Duration.ZERO); // Default value or error handling
                        }
                        break;
                    case "Deposit":
                        deposits.add(currentDeposit); // Add completed deposit to the list
                        break;
                }
            }
        };

        saxParser.parse(new File(xmlPath), handler);
        return deposits;
    }
}
