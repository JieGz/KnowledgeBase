package com.shopline.jdp.options.description;

import java.util.EnumSet;

/**
 * Allows providing multiple formatters for the description. E.g. Html formatter, Markdown formatter etc.
 * 允许为description提供多个格式化器，比如: html格式化器，markdown格式化器等等.
 *
 * @author jieguangzhi
 * @date 2024-03-29
 */
public abstract class Formatter {

    private final StringBuilder state = new StringBuilder();

    public String format(Description description) {
        for (BlockElement blockElement : description.getBlocks()) {
            blockElement.format(this);
        }
        return finalizeFormatting();
    }

    private String finalizeFormatting() {
        String result = state.toString();
        state.setLength(0);
        return result.replaceAll("%%", "%");
    }

    public void format(LinkElement element) {
        formatLink(state, element.getLink(), element.getText());
    }

    public void format(TextElement element) {
        String[] inlineElements =
                element.getElements().stream()
                        .map(
                                el -> {
                                    Formatter formatter = newInstance();
                                    el.format(formatter);
                                    return formatter.finalizeFormatting();
                                })
                        .toArray(String[]::new);
        formatText(
                state,
                escapeFormatPlaceholder(element.getFormat()),
                inlineElements,
                element.getStyles());
    }

    public void format(LineBreakElement element) {
        formatLineBreak(state);
    }

    public void format(ListElement element) {
        String[] inlineElements =
                element.getEntries().stream()
                        .map(
                                el -> {
                                    Formatter formatter = newInstance();
                                    el.format(formatter);
                                    return formatter.finalizeFormatting();
                                })
                        .toArray(String[]::new);
        formatList(state, inlineElements);
    }

    protected abstract void formatList(StringBuilder state, String[] entries);

    protected abstract void formatText(
            StringBuilder state,
            String format,
            String[] elements,
            EnumSet<TextElement.TextStyle> styles);

    protected abstract Formatter newInstance();

    protected abstract void formatLineBreak(StringBuilder state);

    protected abstract void formatLink(StringBuilder state, String link, String description);

    private static final String TEMPORARY_PLACEHOLDER = "randomPlaceholderForStringFormat";

    private static String escapeFormatPlaceholder(String value) {
        return value.replaceAll("%s", TEMPORARY_PLACEHOLDER)
                .replaceAll("%", "%%")
                .replaceAll(TEMPORARY_PLACEHOLDER, "%s");
    }
}
