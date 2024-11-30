package com.bankdeposits.parser;

import com.bankdeposits.model.DepositModel;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StAXParser {
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
                        currentDeposit.setId(reader.getAttributeValue(null, "id"));
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
                                    currentDeposit.setAmount(new BigDecimal(text));
                                    break;
                                case "Profitability":
                                    currentDeposit.setProfitability(new BigDecimal(text));
                                    break;
                                case "TimeConstraints":
                                    currentDeposit.setTimeConstraints(
                                            DatatypeFactory.newInstance().newDuration(text)
                                    );
                                    break;
                            }
                        }
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String endElementName = reader.getLocalName();

                    if ("Deposit".equals(endElementName)) {
                        deposits.add(currentDeposit);
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