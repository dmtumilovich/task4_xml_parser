package by.epam.task4.xml_parser.service;

import by.epam.task4.xml_parser.entity.Tag;
import by.epam.task4.xml_parser.entity.TagType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagParser {

    private static TagParser instance;

    private static final Pattern PATTERN_DECLARATION_TAG = Pattern.compile("<\\?(.+)\\?>");
    private static final Pattern PATTERN_OPEN_TAG = Pattern.compile("<(.+)>");
    private static final Pattern PATTERN_CLOSE_TAG = Pattern.compile("</(.+)>");
    private static final Pattern PATTERN_WITHOUT_BODY_TAG = Pattern.compile("<(.+)/>");

    private static final Pattern PATTERN_TAG_CONTENT = Pattern.compile("<[/?]?([A-Za-z0-9]+).*/?>");
    private static final Pattern PATTERN_TAG_ATTR = Pattern.compile("([\\w\\.-]+)\\s*=\\s*\"([\\w\\.]+)\"");

    private static final String WHITE_SPACE = " ";

    private TagParser() {

    }

    public static TagParser getInstance() {
        if(instance == null)
            instance = new TagParser();
        return instance;
    }

    public Tag parse(String tagString) {
        if (tagString == null)
            return null;

        Tag tag = new Tag();
        tag.setType(parseForType(tagString));
        tag.setName(parseForName(tagString));
        tag.setAttrs(parseForAttrs(tagString));

        return tag;
    }

    private String parseForName(String tagString) {
        Matcher tagContentMatcher = PATTERN_TAG_CONTENT.matcher(tagString);
        if(!tagContentMatcher.find()) {
            return tagString;
        } else {
            String tagContent = tagContentMatcher.group(1);
            return tagContent.split(WHITE_SPACE)[0];
        }
    }

    private Map<String, Object> parseForAttrs(String tagString) {
        Map<String, Object> attrs = new HashMap<>();

        Matcher attrMatcher = PATTERN_TAG_ATTR.matcher(tagString);
        while(attrMatcher.find()) {
            attrs.put(attrMatcher.group(1), attrMatcher.group(2));
        }

        return attrs;
    }

    private TagType parseForType(String tagString) {

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
