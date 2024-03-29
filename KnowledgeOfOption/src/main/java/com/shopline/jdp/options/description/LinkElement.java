package com.shopline.jdp.options.description;

/**
 * @author jieguangzhi
 * @date 2024-03-29
 */
public class LinkElement implements InlineElement {

    private final String link;
    private final String text;


    private LinkElement(String link, String text) {
        this.link = link;
        this.text = text;
    }

    public static LinkElement link(String link, String text) {
        return new LinkElement(link, text);
    }

    public static LinkElement link(String link) {
        return new LinkElement(link, link);
    }

    public String getLink() {
        return link;
    }

    public String getText() {
        return text;
    }

    @Override
    public void format(Formatter formatter) {
        formatter.format(this);
    }
}
