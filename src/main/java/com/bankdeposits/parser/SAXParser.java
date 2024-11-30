package com.bankdeposits.parser;

import com.bankdeposits.model.DepositModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SAXParser {
    public List<DepositModel> parse(String xmlPath) throws Exception {
        List<DepositModel> deposits = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {
            private DepositModel currentDeposit;
            private StringBuilder elementValue = new StringBuilder();

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if ("Deposit".equals(qName)) {
                    currentDeposit = new DepositModel();
                    currentDeposit.setId(attributes.getValue("id"));
                }
                elementValue.setLength(0);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                elementValue.append(ch, start, length);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                String value = elementValue.toString().trim();

                switch (qName) {
                    case "Name":
                        currentDeposit.setName(value);
                        break;
                    case "Country":
                        currentDeposit.setCountry(value);
                        break;
                    case "Type":
                        currentDeposit.setType(value);
                        break;
                    case "Depositor":
                        currentDeposit.setDepositor(value);
                        break;
                    case "Amount":
                        currentDeposit.setAmount(new BigDecimal(value));
                        break;
                    case "Profitability":
                        currentDeposit.setProfitability(new BigDecimal(value));
                        break;
                    case "TimeConstraints":
                        currentDeposit.setTimeConstraints(
                                DatatypeFactory.newInstance().newDuration(value)
                        );
                        break;
                    case "Deposit":
                        deposits.add(currentDeposit);
                        break;
                }
            }
        };

        saxParser.parse(new File(xmlPath), handler);
        return deposits;
    }
}