package by.epam.task4.xml_parser.dao;

import java.io.*;

public class XmlReader implements Closeable {

    private static final String TAG_BEGIN = "<";
    private static final String TAG_END = ">";

    private static final int END_OF_FILE = -1;

    private BufferedReader reader;
    private boolean isTagOpened = false;

    public XmlReader(String filename) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filename));
    }

    public String nextTagString() throws IOException {
        StringBuilder tagBuilder = new StringBuilder();
        String breakPoint = TAG_END;

        while(true) {
            int readSymbol = reader.read();

            if(readSymbol == END_OF_FILE)
                return null;

            String symbol = Character.toString((char)readSymbol);

            if(symbol.equals("\n") || symbol.equals("\t"))
                continue;

            if(isTagOpened) {
                isTagOpened = false;
                tagBuilder.append(TAG_BEGIN);
            }

            if(tagBuilder.length() > 0 && tagBuilder.charAt(0) != '<') {
                if(symbol.equals(TAG_BEGIN)) {
                    isTagOpened = true;
                    break;
                }

                tagBuilder.append(symbol);
                continue;
            }

            tagBuilder.append(symbol);

            if(symbol.equals(breakPoint)) {
                break;
            }

        }

        return tagBuilder.toString();
    }

    @Override
    public void close() throws IOException {
        if(reader != null) {
            reader.close();
        }
    }
}
