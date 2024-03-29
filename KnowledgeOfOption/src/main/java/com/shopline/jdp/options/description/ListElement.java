package com.shopline.jdp.options.description;

import java.util.Arrays;
import java.util.List;

/**
 * @author jieguangzhi
 * @date 2024-03-29
 */
public class ListElement implements BlockElement {

    private final List<InlineElement> entries;

    private ListElement(List<InlineElement> entries) {
        this.entries = entries;
    }

    public static ListElement list(InlineElement... elements) {
        return new ListElement(Arrays.asList(elements));
    }

    public List<InlineElement> getEntries() {
        return entries;
    }


    @Override
    public void format(Formatter formatter) {
        formatter.format(this);
    }
}
