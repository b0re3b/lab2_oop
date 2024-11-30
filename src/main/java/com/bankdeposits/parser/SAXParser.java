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

public class SAXParser {
    public List<DepositModel> parse(String xmlPath) throws Exception {
        List<DepositModel> deposits = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {
            private DepositModel currentDeposit;
            private StringBuilder elementValue = new StringBuilder();

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if ("Deposit".equals(qName)) {
                    currentDeposit = new DepositModel();
                    currentDeposit.setId(attributes.getValue("id"));
                }
                elementValue.setLength(0); // очистити значення елемента
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
                            currentDeposit.setAmount(BigDecimal.ZERO);
                        }
                        break;
                    case "Profitability":
                        try {
                            currentDeposit.setProfitability(new BigDecimal(value));
                        } catch (NumberFormatException e) {
                            currentDeposit.setProfitability(BigDecimal.ZERO);
                        }
                        break;
                    case "TimeConstraints":
                        try {
                            // Перетворення значення на Duration (якщо воно зберігається як число)
                            long timeInDays = Long.parseLong(value);
                            currentDeposit.setTimeConstraints(Duration.ofDays(timeInDays));
                        } catch (Exception e) {
                            currentDeposit.setTimeConstraints(Duration.ZERO); // або інша обробка помилки
                        }
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
