package com.shopline.jdp.options.description;

/**
 * 描述ConfigOptions中使用的枚举常量。
 * 对于用作配置选项的枚举，可以实现这个接口，为每个枚举常量提供描述。在为配置选项生成文档时，会使用这个选项，在文档中包含可用值的列表以及它们各自的描述。
 * 更准确地说，只有InlineElement可以返回，因为块元素不能嵌套到列表中。
 *
 * @author jieguangzhi
 * @date 2024-03-29
 */
public interface DescriptionEnum {

    InlineElement getDescription();
}
