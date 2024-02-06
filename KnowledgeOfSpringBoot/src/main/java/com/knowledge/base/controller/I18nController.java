package com.knowledge.base.controller;

import com.knowledge.base.enums.CodeEnum;
import com.knowledge.base.request.Student;
import com.knowledge.base.response.ResultData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author jieguangzhi
 * @date 2024-01-30
 */
@RestController
@Validated
public class I18nController {

    @GetMapping("/api/test")
    public ResultData test(@RequestParam int testNum) {
        if (1 == testNum) {
            return ResultData.success(CodeEnum.SUCCESS);
        }
        if (2 == testNum) {
            return ResultData.success(CodeEnum.FAIL);
        }
        if (3 == testNum) {
            return ResultData.success("自定义的返回语");
        }
        return ResultData.success(CodeEnum.SUCCESS);
    }


    @PostMapping("/user")
    public ResultData<?> user(@Valid @RequestBody Student student) {
        return ResultData.success("userToken");
    }

}
