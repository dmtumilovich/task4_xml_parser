package by.epam.task4.xml_parser.service;

import by.epam.task4.xml_parser.dao.XmlReader;
import by.epam.task4.xml_parser.entity.Tag;

import java.io.*;

public class XmlParser implements Closeable {

    private XmlReader xmlReader;
    private TagParser tagParser = TagParser.getInstance();

    public XmlParser(String filename) throws FileNotFoundException {
        xmlReader = new XmlReader(filename);
    }

    public Tag nextTag() throws IOException {
        String tagString = xmlReader.nextTagString();
        return tagParser.parse(tagString);
    }

    @Override
    public void close() throws IOException {
        if(xmlReader != null)
            xmlReader.close();
    }

}
