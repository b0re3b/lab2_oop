package com.bankdeposits.parser;

import com.bankdeposits.model.DepositModel;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * A parser for parsing XML files into a list of {@link DepositModel} objects using StAX.
 */
public class StAXParser {

    /**
     * Parses an XML file and converts it into a list of {@link DepositModel} objects.
     *
     * @param xmlPath the path to the XML file to be parsed.
     * @return a list of parsed {@link DepositModel} objects.
     * @throws Exception if an error occurs during parsing.
     */
    public List<DepositModel> parse(String xmlPath) throws Exception {
        List<DepositModel> deposits = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(xmlPath));

        DepositModel currentDeposit = null;
        String currentElement = null;

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String elementName = reader.getLocalName();

                    if ("Deposit".equals(elementName)) {
                        currentDeposit = new DepositModel();
                        currentDeposit.setId(reader.getAttributeValue(null, "id")); // Parse "id" attribute
                    }

                    currentElement = elementName;
                    break;

                case XMLStreamConstants.CHARACTERS:
                    if (currentDeposit != null && currentElement != null) {
                        String text = reader.getText().trim();

                        if (!text.isEmpty()) {
                            switch (currentElement) {
                                case "Name":
                                    currentDeposit.setName(text);
                                    break;
                                case "Country":
                                    currentDeposit.setCountry(text);
                                    break;
                                case "Type":
                                    currentDeposit.setType(text);
                                    break;
                                case "Depositor":
                                    currentDeposit.setDepositor(text);
                                    break;
                                case "Amount":
                                    try {
                                        currentDeposit.setAmount(new BigDecimal(text));
                                    } catch (NumberFormatException e) {
                                        currentDeposit.setAmount(BigDecimal.ZERO); // Default value or error handling
                                    }
                                    break;
                                case "Profitability":
                                    try {
                                        currentDeposit.setProfitability(new BigDecimal(text));
                                    } catch (NumberFormatException e) {
                                        currentDeposit.setProfitability(BigDecimal.ZERO); // Default value or error handling
                                    }
                                    break;
                                case "TimeConstraints":
                                    try {
                                        long timeInDays = Long.parseLong(text); // Parse string as days
                                        currentDeposit.setTimeConstraints(Duration.ofDays(timeInDays));
                                    } catch (NumberFormatException e) {
                                        currentDeposit.setTimeConstraints(Duration.ZERO); // Default value or error handling
                                    }
                                    break;
                            }
                        }
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String endElementName = reader.getLocalName();

                    if ("Deposit".equals(endElementName)) {
                        deposits.add(currentDeposit); // Add the deposit to the list
                        currentDeposit = null;
                    }

                    currentElement = null;
                    break;
            }
        }

        reader.close();
        return deposits;
    }
}
