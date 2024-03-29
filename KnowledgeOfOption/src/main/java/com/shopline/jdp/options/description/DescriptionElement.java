package com.shopline.jdp.options.description;

/**
 * Part of a  Description that can be converted into String representation.
 * <p>
 * 描述的一部分，可转换为字符串表示形式。
 *
 * @author jieguangzhi
 * @date 2024-03-29
 */
public interface DescriptionElement {

    void format(Formatter formatter);
}
