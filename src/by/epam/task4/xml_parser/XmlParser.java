package by.epam.task4.xml_parser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser implements Closeable {

    private static final Pattern PATTERN_DECLARATION_TAG = Pattern.compile("<\\?(.+)\\?>");
    private static final Pattern PATTERN_OPEN_TAG = Pattern.compile("<(.+)>");
    private static final Pattern PATTERN_CLOSE_TAG = Pattern.compile("</(.+)>");
    private static final Pattern PATTERN_WITHOUT_BODY_TAG = Pattern.compile("<(.+)/>");

    private static final Pattern PATTERN_TAG_CONTENT = Pattern.compile("<\\??/?(.+)/?\\??>");
    private static final Pattern PATTERN_TAG_ATTR = Pattern.compile("\\s(.+)\\s*=\\s*\"(.+)\"\\s*");

    private static final String TAG_BEGIN = "<";
    private static final String TAG_END = ">";

    private static final int END_OF_FILE = -1;

    private BufferedReader reader;
    private boolean isTagOpened = false;

    public XmlParser(String filename) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filename));
    }

    public Tag nextTag() throws IOException {
        return parseTag(findNextTag());
    }

    @Override
    public void close() throws IOException {
        if(reader != null)
            reader.close();
    }

    private Tag parseTag(String tagString) {
        if(tagString == null)
            return null;

        Tag tag = new Tag();
        tag.setType(parseTagForType(tagString));
        tag.setName(parseTagForName(tagString));
        tag.setAttrs(parseTagForAttrs(tagString));

        return tag;
    }

    private String findNextTag() throws IOException {

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

    private String parseTagForName(String tagString) {
        Matcher tagContentMatcher = PATTERN_TAG_CONTENT.matcher(tagString);
        if(!tagContentMatcher.find()) {
            return tagString;
        } else {
            String tagContent = tagContentMatcher.group(1);
            return tagContent.split(" ")[0];
        }
    }

    private Map<String, Object> parseTagForAttrs(String tagString) {
        Map<String, Object> attrs = new HashMap<>();

        Matcher attrMatcher = PATTERN_TAG_ATTR.matcher(tagString);
        while(attrMatcher.find()) {
            attrs.put(attrMatcher.group(1), attrMatcher.group(2));
        }

        return attrs;
    }

    private TagType parseTagForType(String tagString) {

        Matcher openTagMatcher = PATTERN_OPEN_TAG.matcher(tagString);
        Matcher closeTagMatcher = PATTERN_CLOSE_TAG.matcher(tagString);
        Matcher withoutBodyTagMatcher = PATTERN_WITHOUT_BODY_TAG.matcher(tagString);
        Matcher declarationTagMatcher = PATTERN_DECLARATION_TAG.matcher(tagString);

        if(declarationTagMatcher.lookingAt()) {
            return TagType.DECLARATION;
        }
        if(closeTagMatcher.lookingAt()) {
            return TagType.CLOSE;
        }
        if(withoutBodyTagMatcher.lookingAt()) {
            return TagType.WITHOUT_BODY;
        }
        if(openTagMatcher.lookingAt()) {
            return TagType.OPEN;
        }
        return TagType.CONTENT;
    }


}
