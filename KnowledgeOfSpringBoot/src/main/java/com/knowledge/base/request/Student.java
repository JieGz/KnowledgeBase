package com.knowledge.base.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author jieguangzhi
 * @date 2024-01-31
 */
@Data
public class Student {

    @NotBlank(message = "name.notBlank")
    private String name;
}
