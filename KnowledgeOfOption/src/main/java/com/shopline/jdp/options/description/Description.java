package com.shopline.jdp.options.description;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jieguangzhi
 * @date 2024-03-29
 */
public class Description {
    private final List<BlockElement> blocks;

    private Description(List<BlockElement> blocks) {
        this.blocks = blocks;
    }

    public List<BlockElement> getBlocks() {
        return blocks;
    }

    public static DescriptionBuilder builder() {
        return new DescriptionBuilder();
    }

    public static class DescriptionBuilder {
        private final List<BlockElement> blocks = new ArrayList<>();

        public DescriptionBuilder text(String format, InlineElement... elements) {
            blocks.add(TextElement.text(format, elements));
            return this;
        }

        public DescriptionBuilder text(String text) {
            blocks.add(TextElement.text(text));
            return this;
        }

        public DescriptionBuilder add(BlockElement block) {
            blocks.add(block);
            return this;
        }

        public DescriptionBuilder linebreak() {
            blocks.add(LineBreakElement.linebreak());
            return this;
        }

        public DescriptionBuilder list(InlineElement... elements) {
            blocks.add(ListElement.list(elements));
            return this;
        }

        public Description build() {
            return new Description(blocks);
        }
    }
}
