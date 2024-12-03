package com.bankdeposits.parser;

import com.bankdeposits.model.DepositModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * A parser for parsing XML files into a list of {@link DepositModel} objects using DOM.
 */
public class DOMParser {

    /**
     * Parses an XML file and converts it into a list of {@link DepositModel} objects.
     *
     * @param xmlPath the path to the XML file to be parsed.
     * @return a list of parsed {@link DepositModel} objects.
     * @throws Exception if an error occurs during parsing.
     */
    public List<DepositModel> parse(String xmlPath) throws Exception {
        List<DepositModel> deposits = new ArrayList<>();

        // Set up the DOM parser
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlPath));

        // Get all "Deposit" elements
        NodeList depositNodes = document.getElementsByTagName("Deposit");

        for (int i = 0; i < depositNodes.getLength(); i++) {
            Element depositElement = (Element) depositNodes.item(i);

            DepositModel deposit = new DepositModel();
            deposit.setId(depositElement.getAttribute("id")); // Parse "id" attribute

            // Parse child elements of "Deposit"
            deposit.setName(getElementTextContent(depositElement, "Name"));
            deposit.setCountry(getElementTextContent(depositElement, "Country"));
            deposit.setType(getElementTextContent(depositElement, "Type"));
            deposit.setDepositor(getElementTextContent(depositElement, "Depositor"));

            // Parse "Amount" and handle potential errors
            try {
                deposit.setAmount(new BigDecimal(getElementTextContent(depositElement, "Amount")));
            } catch (NumberFormatException e) {
                deposit.setAmount(BigDecimal.ZERO); // Default value or custom handling
            }

            // Parse "Profitability" and handle potential errors
            try {
                deposit.setProfitability(new BigDecimal(getElementTextContent(depositElement, "Profitability")));
            } catch (NumberFormatException e) {
                deposit.setProfitability(BigDecimal.ZERO); // Default value or custom handling
            }

            // Parse "TimeConstraints" and handle potential errors
            try {
                String timeConstraintString = getElementTextContent(depositElement, "TimeConstraints");
                Duration timeConstraints = Duration.parse(timeConstraintString); // Convert string to Duration
                deposit.setTimeConstraints(timeConstraints);
            } catch (Exception e) {
                deposit.setTimeConstraints(Duration.ZERO); // Default value or custom handling
            }

            deposits.add(deposit);
        }

        return deposits;
    }

    /**
     * Retrieves the text content of a child element by its name.
     *
     * @param parent      the parent element.
     * @param elementName the name of the child element.
     * @return the text content of the child element, or an empty string if the element is not found.
     */
    private String getElementTextContent(Element parent, String elementName) {
        NodeList nodeList = parent.getElementsByTagName(elementName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return ""; // Default value or custom handling
        }
    }
}
