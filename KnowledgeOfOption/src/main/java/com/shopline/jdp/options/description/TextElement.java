package com.shopline.jdp.options.description;

import com.shopline.jdp.utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * @author jieguangzhi
 * @date 2024-03-29
 */
public class TextElement implements InlineElement, BlockElement {

    private final String format;
    private final List<InlineElement> elements;
    private final EnumSet<TextStyle> textStyles = EnumSet.noneOf(TextStyle.class);

    private TextElement(String format, List<InlineElement> elements) {
        this.format = format;
        this.elements = elements;
    }

    public static TextElement text(String format, InlineElement... elements) {
        return new TextElement(format, Arrays.asList(elements));
    }

    public static TextElement text(String text) {
        return new TextElement(text, Collections.emptyList());
    }

    public static TextElement code(String text) {
        TextElement element = text(text);
        element.textStyles.add(TextStyle.CODE);
        return element;
    }

    public static InlineElement wrap(InlineElement... elements) {
        return text(StringUtils.repeat("%s", elements.length), elements);
    }

    public String getFormat() {
        return format;
    }

    public List<InlineElement> getElements() {
        return elements;
    }

    public EnumSet<TextStyle> getStyles() {
        return textStyles;
    }

    @Override
    public void format(Formatter formatter) {
        formatter.format(this);
    }


    /** Styles that can be applied to {@link TextElement} e.g. code, bold etc. */
    public enum TextStyle {
        CODE
    }
}
