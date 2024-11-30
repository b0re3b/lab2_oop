package com.bankdeposits.parser;

import com.bankdeposits.model.DepositModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DOMParser {
    public List<DepositModel> parse(String xmlPath) throws Exception {
        List<DepositModel> deposits = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlPath));

        NodeList depositNodes = document.getElementsByTagName("Deposit");

        for (int i = 0; i < depositNodes.getLength(); i++) {
            Element depositElement = (Element) depositNodes.item(i);

            DepositModel deposit = new DepositModel();
            deposit.setId(depositElement.getAttribute("id"));

            deposit.setName(getElementTextContent(depositElement, "Name"));
            deposit.setCountry(getElementTextContent(depositElement, "Country"));
            deposit.setType(getElementTextContent(depositElement, "Type"));
            deposit.setDepositor(getElementTextContent(depositElement, "Depositor"));

            deposit.setAmount(new BigDecimal(
                    getElementTextContent(depositElement, "Amount")
            ));

            deposit.setProfitability(new BigDecimal(
                    getElementTextContent(depositElement, "Profitability")
            ));

            deposit.setTimeConstraints(
                    DatatypeFactory.newInstance().newDuration(
                            getElementTextContent(depositElement, "TimeConstraints")
                    )
            );

            deposits.add(deposit);
        }

        return deposits;
    }

    private String getElementTextContent(Element parent, String elementName) {
        NodeList nodeList = parent.getElementsByTagName(elementName);
        return nodeList.item(0).getTextContent();
    }
}