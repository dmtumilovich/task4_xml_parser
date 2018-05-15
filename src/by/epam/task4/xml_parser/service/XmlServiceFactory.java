package by.epam.task4.xml_parser.service;

import java.io.FileNotFoundException;

public class XmlServiceFactory {

    private static final XmlServiceFactory instance = new XmlServiceFactory();

    private XmlServiceFactory() {}

    public XmlParser getXmlParser(String filename) throws FileNotFoundException {
        return new XmlParser(filename);
    }

    public static XmlServiceFactory getInstance() {
        return instance;
    }

}
