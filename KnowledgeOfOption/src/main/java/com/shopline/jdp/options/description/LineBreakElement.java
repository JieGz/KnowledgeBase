package com.shopline.jdp.options.description;

/**
 * @author jieguangzhi
 * @date 2024-03-29
 */
public class LineBreakElement implements InlineElement, BlockElement {

    public static LineBreakElement linebreak() {
        return new LineBreakElement();
    }

    private LineBreakElement() {
    }

    @Override
    public void format(Formatter formatter) {
        formatter.format(this);
    }
}
