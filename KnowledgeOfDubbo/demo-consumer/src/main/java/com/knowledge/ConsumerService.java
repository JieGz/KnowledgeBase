package com.knowledge;

import com.shopline.datacenter.apiDataServer.api.ApiDataServerResultCode;
import com.shopline.datacenter.apiDataServer.api.DaasQueryService;
import com.shopline.datacenter.apiDataServer.api.request.PageApiRequest;
import com.shopline.datacenter.apiDataServer.api.response.ApiPageResponse;
import com.yy.onepiece.commons.result.generic.DataResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jieguangzhi
 * @date 2023-11-27
 */
@Service
public class ConsumerService {

    @DubboReference
    private DemoService demoService;

    @DubboReference(check = false, version = "1.0.0")
    private DaasQueryService daasQueryService;


    public String test1() {
        final String sayHello = demoService.sayHello("Luke");
        System.out.println(sayHello);
        return sayHello;
    }

    public String test2() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "sl_in_jieguangzhi");


        PageApiRequest pageQueryApiRequest = PageApiRequest.builder()
                .apiSeq("luke_test_api")
                .pageNo(1L)
                .pageSize(10L)
                .map(map)
                .build();

        final DataResult<ApiDataServerResultCode, ApiPageResponse> result = this.daasQueryService.pageQueryApi(pageQueryApiRequest);

        return result.toString();
    }
}
