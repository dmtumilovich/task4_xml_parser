package by.epam.task4;

import by.epam.task4.xml_parser.Tag;
import by.epam.task4.xml_parser.XmlParser;

import java.io.IOException;

public class Main {
    private static final String FILE_NAME_1 = "src/res/notes.xml";
    private static final String FILE_NAME_2 = "src/res/breakfast.xml";

    public static void main(String[] args) throws IOException {

        XmlParser parserReader = new XmlParser(FILE_NAME_1);
        Tag tag;
        while((tag = parserReader.nextTag()) != null) {
            System.out.println(tag);
        }
        parserReader.close();

        System.out.println("****");

        parserReader = new XmlParser(FILE_NAME_2);
        while((tag = parserReader.nextTag()) != null) {
            System.out.println(tag);
        }
        parserReader.close();
    }
}
