package com.knowledge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jieguangzhi
 * @date 2023-11-28
 */
@RestController
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/test1")
    public String test1() {
        System.out.println("11111111");
        return consumerService.test1();
    }

    @GetMapping("/test2")
    public String test2() {
        return consumerService.test2();
    }
}
