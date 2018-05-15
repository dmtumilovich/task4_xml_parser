package by.epam.task4.xml_parser.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Tag {
    private String name;
    private TagType type;
    private Map<String, Object> attrs = new HashMap<>();

    public Tag() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("type: ").append(type).append(", ")
                .append("name: ").append(name).append(", ")
                .append("attrs: ");

        if(attrs.size() == 0) {
            builder.append("none");
            return builder.toString();
        }

        Set<Map.Entry<String, Object>> attrsSet = attrs.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = attrsSet.iterator();

        while(iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            builder.append(entry.getKey()).append(" = ")
                    .append(entry.getValue()).append(" ");
        }

        return builder.toString();
    }
}
