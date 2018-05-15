package by.epam.task4.xml_parser.main;

import by.epam.task4.xml_parser.entity.Tag;
import by.epam.task4.xml_parser.service.XmlParser;
import by.epam.task4.xml_parser.service.XmlServiceFactory;

import java.io.IOException;

public class Main {
    private static final String FILE_NAME_1 = "src/res/notes.xml";
    private static final String FILE_NAME_2 = "src/res/breakfast.xml";

    public static void main(String[] args) throws IOException {

        XmlServiceFactory xmlServiceFactory = XmlServiceFactory.getInstance();
        XmlParser xmlParser = xmlServiceFactory.getXmlParser(FILE_NAME_1);

        Tag tag;
        while((tag = xmlParser.nextTag()) != null) {
            System.out.println(tag);
        }
        xmlParser.close();

        System.out.println("****");

        xmlParser = xmlServiceFactory.getXmlParser(FILE_NAME_2);
        while((tag = xmlParser.nextTag()) != null) {
            System.out.println(tag);
        }
        xmlParser.close();
    }
}
